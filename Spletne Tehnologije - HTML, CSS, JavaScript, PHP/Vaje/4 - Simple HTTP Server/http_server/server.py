"""An example of a simple HTTP server."""
from __future__ import print_function

import socket
import mimetypes

# Port number
PORT = 8080

# Header template for a successful HTTP request
# Return this header (+ content) when the request can be
# successfully fulfilled
HEADER_RESPONSE_200 = """HTTP/1.1 200 OK
Content-Type: %s
Content-Length: %d
Connection: Close

"""

# Template for a 404 (Not found) error: return this when
# the requested resource is not found
RESPONSE_404 = """HTTP/1.1 404 Not found
Content-Type: text/html; charset=utf-8
Connection: Close

<!DOCTYPE html>
<h1>404 Page not found</h1>
<p>Page cannot be found.</p>
"""


def parse_headers(client):
    headers = {}
    while True:
        line = client.readline().decode("utf-8").strip()
        if line == "":
            return headers
        key, value = line.split(":", 1)
        headers[key.strip()] = value.strip()


def process_request(connection, address, port):
    """
    Process an incoming socket request.

    :param connection: the socket object used to send and receive data
    :param address: the address (IP) of the remote socket
    :param port: the port number of the remote socket
    """

    # Make reading from a socket like reading/writing from a file
    # Use binary mode, so we can read and write binary data. However,
    # this also means that we have to decode (and encode) data (preferably
    # to utf-8) when reading (and writing) text
    client = connection.makefile("wrb")

    # Read one line, decode it to utf-8 and strip leading and trailing spaces
    line = client.readline().decode("utf-8").strip()
    try:
        verb, uri, version = line.split(" ")
        assert verb in ["GET", "POST"], "Only GET requests are supported"
        assert uri[0] == "/", "Invalid URI"
        assert version == "HTTP/1.1", "Invalid protocol version"
    except Exception as e:
        print("[%s:%d]Exception parsing '%s': %s" % (address, port, line, e))
        return

    uri = "index.html" if uri == "/" else uri[1:]
    headers = parse_headers(client)
    try:
        with open(uri, "rb") as f:
            content = f.read()
            f.close()
        mime_type, _ = mimetypes.guess_type(uri[1:])
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


def main():
    """Starts the server and waits for connections."""

    # Create a TCP socket
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # To prevent "Address already in use" error while restarting the server,
    # set the reuse address socket option
    server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

    # Bind on all network addresses (interfaces)
    server.bind(("", PORT))

    # Start listening and allow at most 1 queued connection
    server.listen(1)

    print("Listening on %d" % PORT)

    while True:
        # Accept the connection
        connection, (address, port) = server.accept()
        print("[%s:%d] CONNECTED" % (address, port))

        # Process request
        process_request(connection, address, port)

        # Close the socket
        connection.close()
        print("[%s:%d] DISCONNECTED" % (address, port))


if __name__ == "__main__":
    main()
