startGame();

function startGame(){
    var canvas          = document.getElementById("renderCanvas");
    var engine          = new BABYLON.Engine(canvas, true);
    document.addEventListener("click", function() {
        engine.switchFullscreen(true);
        engine.requestPointerLock();
    });
    var scene           = new BABYLON.Scene(engine);
    var assets = [];
    var score = 0;
    var traveledCubes = 0;
    var playing = false;
    var velocity = new BABYLON.Vector3(0, 0, 0);
    var onGround = false;
    var gravity = -0.5;
    var cubes = [];
    // Skybox
    var skybox = BABYLON.Mesh.CreateBox("skyBox", 250.0, scene);
    var skyboxMaterial = new BABYLON.StandardMaterial("skyBox", scene);
    skyboxMaterial.backFaceCulling = false;
    skyboxMaterial.reflectionTexture = new BABYLON.CubeTexture("assets/skybox/TropicalSunnyDay", scene);
    skyboxMaterial.reflectionTexture.coordinatesMode = BABYLON.Texture.SKYBOX_MODE;
    skyboxMaterial.diffuseColor = new BABYLON.Color3(0, 0, 0);
    skyboxMaterial.specularColor = new BABYLON.Color3(0, 0, 0);
    skybox.material = skyboxMaterial;

    // Create camera
    var camera = new BABYLON.ArcRotateCamera("ArcRotateCamera", -Math.PI/2, Math.PI/2, 0.9, new BABYLON.Vector3(0, 10, 0), scene);

    // Create light
    var light = new BABYLON.PointLight("Omni0", new BABYLON.Vector3(0, 50, 0), scene);

    // Loader for menu assets
    var loader = new BABYLON.AssetsManager(scene);
    var toLoad = [
        {name : "startButton", src : "assets/startButton.gif" }
    ];
    toLoad.forEach(function(obj) {
        var img = loader.addTextureTask(obj.name, obj.src);
        img.onSuccess = function(t) {
            assets[t.name] = t.texture;
        };
    });

    loader.onFinish = function() {
        var value;
        var gui;
        setTimeout(function() {
            /* GUI CREATION when all texture are loaded*/
            gui = new bGUI.GUISystem(scene, engine.getRenderWidth(), engine.getRenderHeight());
            gui.enableClick();
            // Start Button
            var start = new bGUI.GUIPanel("button", assets["startButton"], null, gui);
            start.relativePosition(new BABYLON.Vector3(0.5, 0.5, 0));
            start.onClick = function() {
                start.setVisible(false);
                playing = true;
                setTimeout(function() {
                    // Ingame camera
                    camera = new BABYLON.FreeCamera("cam", new BABYLON.Vector3(0, 10, 0), scene);
                    camera.maxZ = 500;
                    camera.ellipsoid = new BABYLON.Vector3(1, 1, 1);
                    camera.applyGravity = true;
                    camera.checkCollisions = true;
                    camera.attachControl(canvas, true);
                    scene.activeCamera = camera;

                    // Ingame GUI
                    gui = new bGUI.GUISystem(scene, engine.getRenderWidth(), engine.getRenderHeight());
                    gui.disableClick();

                    // Text
                    var textGroup = new bGUI.GUIGroup("score", gui);

                    // Score
                    var label = new bGUI.GUIText("scoreLabel", 256, 128, {font:"40px Segoe UI", text:"Score: ", color:"#ffffff"}, gui);
                    label.guiposition(new BABYLON.Vector3(50, 50, 0));
                    textGroup.add(label);

                    // Value
                    var font = {font:"40px Segoe UI", text:"PLACEHOLDER", color:"#ffffff"};
                    font.text = score;
                    value = new bGUI.GUIText("scoreValue", 256, 128, font, gui);
                    value.guiposition(new BABYLON.Vector3(300, 50, 0));
                    textGroup.add(value);

                    gui.updateCamera();
                }, 10);
            };

            gui.updateCamera();
        }, 10);
        // Izris kock
        // Setting gravity, collisions for scene
        scene.gravity = new BABYLON.Vector3(0, 0, 0);
        scene.collisionsEnabled = true;
        engine.isPointerLock = true

        cubes = createCubes(engine, scene, canvas, cubes, 0, 60);

        // Event listener za pritisk na tipke
        window.addEventListener("keydown", function(event) {
            switch(event.keyCode){
                case 32: // Space
                    // TODO: Jump
                    if(onGround) {
                        velocity.y += 3;
                        onGround = false;
                    }
                    break;
            }
        }, false);
        window.addEventListener("keyup", function(event){
            switch(event.keyCode){
                case 32: //space
                    if(velocity.y < -0.6){
                        velocity.y = -0.6;
                    }
            }
        })
        // Render loop function with movement
        engine.runRenderLoop(function() {
            if(playing){
                newScore = Math.floor((camera.position.z+5) / 10.0);
                if(score < newScore) score = newScore;
                velocity.y += gravity;
                camera.position = camera.position.add(velocity);
                var cubeIndex = Math.floor((camera.position.z + 5.0)/10)*3 + Math.floor((camera.position.x + 15.0)/10);
                console.log(cubeIndex);
                    if((cubes[cubeIndex].checkCollisions)&&(camera.position.y < cubes[cubeIndex].position.y + 7.0)&&(camera.position.y > cubes[cubeIndex].position.y - 7.0)){
                        camera.position.y = cubes[cubeIndex].position.y + 7.0;
                        velocity.y = 0;
                        onGround = true;
                    }
                    if((camera != null) && (camera.position.y < -200.0)){
                        playing = false;
                        scene.dispose();
                        scene = new BABYLON.Scene(engine);
                        cubes = createCubes(engine, scene, canvas, [], 0, 50);
                        light =  new BABYLON.PointLight("Omni0", new BABYLON.Vector3(0, 50, 0), scene);
                        camera = new BABYLON.ArcRotateCamera("ArcRotateCamera", -Math.PI/2, Math.PI/2, 0.9, new BABYLON.Vector3(0, 10, 0), scene);
                        gui = new bGUI.GUISystem(scene, engine.getRenderWidth(), engine.getRenderHeight());
                        label =  new bGUI.GUIText("scoreLabel", 256, 128, {font:"40px Segoe UI", text:"Score: ", color:"#ffffff"}, gui);
                        label.guiposition(new BABYLON.Vector3(engine.getRenderWidth()/2 - 100, engine.getRenderHeight()/2, 0));
                        font = {font:"40px Segoe UI", text:"PLACEHOLDER", color:"#ffffff"};
                        font.text = score;
                        value = new bGUI.GUIText("scoreValue", 256, 128, font, gui);
                        value.guiposition(new BABYLON.Vector3(engine.getRenderWidth()/2 + 100, engine.getRenderHeight()/2, 0));
                        gui.updateCamera();
                    }
            }
            // Update score value
            if((value != null)&&(playing)){ 
                value.setVisible(false);
                var font = {font:"40px Segoe UI", text:"PLACEHOLDER", color:"#ffffff"};
                font.text = score;
                value = new bGUI.GUIText("scoreValue", 256, 128, font, gui);
                value.guiposition(new BABYLON.Vector3(300, 50, 0));
            }
            light.position = camera.position.add(new BABYLON.Vector3(0, 100, 0));
            skybox.position = camera.position;
            scene.render();
        });
    };

    loader.load();
}

function createCubes(engine, scene, canvas, cubes, startIndex, numCubes) {
    var maxDifference = 5.0;
    var previousY = [0, 0, 0];
    if(!cubes) cubes = [];
    var wallPanes = [];
    var block = 1;
    var blockY = 0;
    for(var i = startIndex; i < startIndex + numCubes; i++){
        if(i == 0){
            // Starting cubes
            for(var j = 0; j < 3; j++){
                cubes[i+j] = BABYLON.Mesh.CreateBox("cube"+j, 10.0, scene);
                cubes[i+j].material = new BABYLON.StandardMaterial("cube"+j+"mat", scene);
                cubes[i+j].material.diffuseColor = new BABYLON.Color3(Math.random(), Math.random(), Math.random());
                cubes[i+j].position.x = -10.0 + (10.0 * j);
                cubes[i+j].checkCollisions = true;
            }
            // Starting walls
            for(var j = 0; j < 2; j++){
                wallPanes[i+j] = BABYLON.Mesh.CreateGround("wall"+index, 100, 15, 2, scene);
                wallPanes[i+j].material = new BABYLON.StandardMaterial("wall"+index+"mat", scene);
                wallPanes[i+j].material.backFaceCulling = false;
                wallPanes[i+j].rotation.z = Math.PI/2 *(-1 + 2*j);
                wallPanes[i+j].position.x = -15 + j * 30;
                wallPanes[i+j].position.z = 0;
                wallPanes[i+j].checkCollisions = true;
            }
            // Back wall
            var backWall = BABYLON.Mesh.CreateGround("backWall", 30, 100, 2, scene);
            backWall.material = new BABYLON.StandardMaterial("backWallMat", scene);
            backWall.material.backFaceCulling = false;
            backWall.rotation.x = Math.PI/2;
            backWall.position.z = -5.0;
            backWall.checkCollisions = true;
        }else if((i > 0)&&(i<25)){
            for(var j = 0; j < 3; j++){
                var newX = -10.0 + (10.0 * j);
                var newY = 0 + (Math.random()*(maxDifference*2) - maxDifference);
                var newZ = i * 10.0;
                cubes[i*3+j] = BABYLON.Mesh.CreateBox("cube"+(i*3+j), 10.0, scene);
                cubes[i*3+j].material = new BABYLON.StandardMaterial("cube"+i+"mat", scene);
                cubes[i*3+j].material.diffuseColor = new BABYLON.Color3(Math.random(), Math.random(), Math.random());
                cubes[i*3+j].checkCollisions = true;
                cubes[i*3+j].position = new BABYLON.Vector3(newX, newY, newZ);
                previousY[j] = newY;
            }
            for(var j = 0; j < 2; j++){
                var index = i * 2 + j;
                wallPanes[index] = BABYLON.Mesh.CreateGround("wall"+index, 100, 10, 2, scene);
                wallPanes[index].material = new BABYLON.StandardMaterial("wall"+index+"mat", scene);
                wallPanes[index].material.backFaceCulling = false;
                wallPanes[index].rotation.z = Math.PI/2 *(-1 + 2 * j);
                wallPanes[index].position.x = -15 + j * 30;
                wallPanes[index].position.z = 0 + i * 10;
                wallPanes[index].checkCollisions = true;
            }
        }else if((i>= 25)){
            if(i == 25){
                var startY = (cubes[i-3].position.y + cubes[i-2].position.y + cubes[i-1].position.y)/3 ;
                // Starting cubes
                for(var j = 0; j < 3; j++){
                    cubes[i*3+j] = BABYLON.Mesh.CreateBox("cube"+j, 10.0, scene);
                    cubes[i*3+j].material = new BABYLON.StandardMaterial("cube"+j+"mat", scene);
                    cubes[i*3+j].material.diffuseColor = new BABYLON.Color3(Math.random(), Math.random(), Math.random());
                    cubes[i*3+j].position.x = -10.0 + (10.0 * j);
                    cubes[i*3+j].position.z = i*10;
                    cubes[i*3+j].checkCollisions = true;
                    cubes[i*3+j].position.y = startY;
                }
                // Starting walls
                for(var j = 0; j < 2; j++){
                    wallPanes[i*2+j] = BABYLON.Mesh.CreateGround("wall"+index, 100, 15, 2, scene);
                    wallPanes[i*2+j].material = new BABYLON.StandardMaterial("wall"+index+"mat", scene);
                    wallPanes[i*2+j].material.backFaceCulling = false;
                    wallPanes[i*2+j].rotation.z = Math.PI/2 *(-1 + 2*j);
                    wallPanes[i*2+j].position.x = -15 + j * 30;
                    wallPanes[i*2+j].position.z = i * 10;
                    wallPanes[i*2+j].checkCollisions = true;
                }
                blockY = startY;
            }else{

                for(var j = 0; j < 3; j++){
                    var newX = -10.0 + (10.0 * j);
                    var newY = blockY + (Math.random()*(maxDifference*2) - maxDifference);
                    var newZ = i * 10.0;
                    cubes[i*3+j] = BABYLON.Mesh.CreateBox("cube"+(i*3+j), 10.0, scene);
                    cubes[i*3+j].material = new BABYLON.StandardMaterial("cube"+i+"mat", scene);
                    cubes[i*3+j].material.diffuseColor = new BABYLON.Color3(Math.random(), Math.random(), Math.random());

                    cubes[i*3+j].position = new BABYLON.Vector3(newX, newY, newZ);

                    if(block == j){
                        cubes[i*3+j].checkCollisions = true;        
                        blockY = newY

                    }else{
                        cubes[i*3+j].dispose();
                    }
                }
                if(block == 0) block++;
                else if(block == 2) block--;
                else{
                    var rand = Math.random();
                    if(rand > 0.5) block--;
                    else block++;
                }
                for(var j = 0; j < 2; j++){
                    var index = i * 2 + j;
                    wallPanes[index] = BABYLON.Mesh.CreateGround("wall"+index, 100, 10, 2, scene);
                    wallPanes[index].material = new BABYLON.StandardMaterial("wall"+index+"mat", scene);
                    wallPanes[index].material.backFaceCulling = false;
                    wallPanes[index].rotation.z = Math.PI/2 *(-1 + 2 * j);
                    wallPanes[index].position.x = -15 + j * 30;
                    wallPanes[index].position.z = 0 + i * 10;
                    wallPanes[index].checkCollisions = true;
                }
            }
        }

    }
    return cubes;
}
