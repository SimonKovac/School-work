"""An example of a simple HTTP server."""
from __future__ import print_function

import mimetypes
import pickle
import socket
from os.path import isdir

try:
    from urllib.parse import unquote_plus
except ImportError:
    from urllib import unquote_plus

# Pickle file for storing data
PICKLE_DB = "db.pkl"

# Directory containing www data
WWW_DATA = "www-data"

# Header template for a successful HTTP request
HEADER_RESPONSE_200 = """HTTP/1.1 200 OK
content-type: %s
content-length: %d
connection: Close

"""

RESPONSE_301 = """HTTP/1.1 301 Moved Permanently
Location: %s

<!doctype html>
<h1>Moved Permanently</h1>
<p>URL permanently redirected</p>
"""

# Represents a table row that holds user data
TABLE_ROW = """
<tr>
    <td>%d</td>
    <td>%s</td>
    <td>%s</td>
</tr>
"""

RESPONSE_400 = """HTTP/1.1 400 Bad request
content-type: text/html
connection: Close

<!doctype html>
<h1>400 Bad request</h1>
<p>Bad request</p>
"""

# Template for a 404 (Not found) error
RESPONSE_404 = """HTTP/1.1 404 Not found
content-type: text/html
connection: Close

<!doctype html>
<h1>404 Page not found</h1>
<p>Page cannot be found.</p>
"""


def save_to_db(first, last):
    """Create a new user with given first and last name and store it into
    file-based database.

    For instance, save_to_db("Mick", "Jagger"), will create a new user
    "Mick Jagger" and also assign him a unique number.

    Do not modify this method."""

    existing = read_from_db()
    existing.append({
        "number": 1 if len(existing) == 0 else existing[-1]["number"] + 1,
        "first": first,
        "last": last
    })
    with open(PICKLE_DB, "wb") as handle:
        pickle.dump(existing, handle)


def read_from_db(criteria=None):
    """Read entries from the file-based DB subject to provided criteria

    Use this method to get users from the DB. The criteria parameters should
    either be omitted (returns all users) or be a dict that represents a query
    filter. For instance:
    - read_from_db({"number": 1}) will return a list of users with number 1
    - read_from_db({"first": "bob"}) will return a list of users whose first
    name is "bob".

    Do not modify this method."""
    if criteria is None:
        criteria = {}
    else:
        # remove empty criteria values
        for key in ("number", "first", "last"):
            if key in criteria and criteria[key] == "":
                del criteria[key]

        # cast number to int
        if "number" in criteria:
            criteria["number"] = int(criteria["number"])

    try:
        with open(PICKLE_DB, "rb") as handle:
            data = pickle.load(handle)

        filtered = []
        for entry in data:
            predicate = True

            for key, val in criteria.items():
                if val != entry[key]:
                    predicate = False

            if predicate:
                filtered.append(entry)

        return filtered
    except (IOError, EOFError):
        return []

def parse_headers(client):
    headers = {}
    while True:
        line = client.readline().decode("utf-8").strip()
        if line == "":
            return headers
        key, value = line.split(":", 1)
        headers[key.strip()] = unquote_plus(value.strip())

def parse_params(string):
    params = {}
    for param in string.split("&"):
        if len(param.split("=", 1)) < 2:
            continue
        key, value = param.split("=", 1)
        params[key.strip()] = value.strip()
    return params

def process_request(connection, address, port):
    """Process an incoming socket request.

    :param connection is a socket of the client
    :param address is a 2-tuple (address(str), port(int)) of the client
    """

    # Make reading from a socket like reading/writing from a file
    # Use binary mode, so we can read and write binary data. However,
    # this also means that we have to decode (and encode) data (preferably
    # to utf-8) when reading (and writing) text
    client = connection.makefile("wrb")

    # Read one line, decode it to utf-8 and strip leading and trailing spaces
    line = client.readline().decode("utf-8").strip()
    headers = parse_headers(client)
    try:
        verb, uri, version = line.split(" ")
        assert verb in ["GET", "POST"], "Only GET requests are supported"
        #assert uri[0] == "/", "Invalid URI"
        assert version == "HTTP/1.1", "Invalid protocol version"
        if verb == "POST":
            assert headers['Content-Length'] is not None and headers['Content-Length'] != ""
    except Exception as e:
        print("[%s:%d]Exception parsing '%s': %s" % (address[0], address[1], line, e))
        client.write(RESPONSE_400.encode("utf-8"))
        client.close()
        return

    if uri[-1] == "/" or isdir("www-data" + uri):
        response = RESPONSE_301 % ("http://" + headers["Host"] + uri + "index.html")
        client.write(response.encode("utf-8"))
        client.close()
        return

    params = None

    if verb == "GET":
        if len(uri.split("?")) > 1:
            uri, params = uri.split("?")
            params = parse_params(unquote_plus(params))

    if uri == "/app-index":
        with open("www-data/app_list.html", "rb") as f:
            template = f.read()
            f.close()
        mime_type = "text/html"
        if mime_type is None:
            mime_type = "application/octet-stream"
        students = ""
        template = template.decode("utf-8")
        for student in read_from_db(params):
            students += TABLE_ROW % (student["number"], student["first"], student["last"])
        template = template.format(students=students)
        template = template.format(students=students).encode("utf-8")
        header = (HEADER_RESPONSE_200 % (mime_type, len(template))).encode("utf-8")
        client.write(header + template)
        client.close()
        return

    if verb == "POST":
        if uri == "/app-add":
            line = client.read(int(headers["Content-Length"])).decode("utf-8")
            params = parse_params(line)
            try:
                save_to_db(params["first"], params["last"])
                mime_type = "text/html"
                with open("www-data/app_add.html", "rb") as f:
                    template = f.read()
                    f.close()
                header = (HEADER_RESPONSE_200 % (mime_type, len(template))).encode("utf-8")
                client.write(header + template)
                client.close()
                return
            except Exception as e:
                client.write(RESPONSE_400.encode("utf-8"))
                client.close()
                return

    uri = "www-data/" + uri[1:]
    try:
        with open(uri, "rb") as f:
            content = f.read()
            f.close()
        mime_type, _ = mimetypes.guess_type(uri[1:])
        if mime_type is None:
            mime_type = "application/octet-stream"
        header = HEADER_RESPONSE_200 % (mime_type, len(content))
        client.write(header.encode("utf-8"))
        client.write(content)
    except IOError as e:
        client.write(RESPONSE_404.encode("utf-8"))
    # Create a response: the same text, but in upper case

    # Write the response to the socket
    # client.write(response.encode("utf-8"))

    # Closes file-like object
    client.close()


def main(port):
    """Starts the server and waits for connections."""

    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server.bind(("", port))
    server.listen(1)

    print("Listening on %d" % port)

    while True:
        connection, address = server.accept()
        print("[%s:%d] CONNECTED" % address)
        process_request(connection, address, port)
        connection.close()
        print("[%s:%d] DISCONNECTED" % address)


if __name__ == "__main__":
    main(8080)
