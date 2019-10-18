##Docker REST API JSON Server:

[JSON Server](https://github.com/typicode/json-server) provides REST API mocking based on plain JSON.
This is a [docker](https://www.docker.io) image that eases setup.
```
    sudo apt-get install docker

    Run and Install server:
        sudo docker run -d -p 65535:80 -v /home/bulat/IdeaProjects/docker-json-server/db.json:/data/db.json clue/json-server

    Open link in the browser:
        http://localhost:65535

    Manage Containers:
        sudo docker ps
        sudo docker stop <container_id>
        sudo docker rm <container_id>
   
    Manage Images:
        sudo docker images
        sudo docker rmi <image_id>
    
    

    Uninstall Docker from OS:
        sudo apt-get purge docker-ce
        sudo rm -rf /var/lib/docker 
```



<br><br><br><br><br><br><br>
<br><br><br><br><br><br><br>




## Usage

This docker image is available as a [trusted build on the docker index](https://index.docker.io/u/clue/json-server/),
so there's no setup required.
Using this image for the first time will start a download automatically.
Further runs will be immediate, as the image will be cached locally.

The recommended way to run this container looks like this:

```bash
$ docker run -d -p 80:80 -v /home/user/db.json:/data/articles.json clue/json-server
```

The above example exposes the JSON Server REST API on port 80, so that you can now browse to:

```
http://localhost/
```

This is a rather common setup following docker's conventions:

* `-d` will run a detached instance in the background
* `-p {OutsidePort}:80` will bind the webserver to the given outside port
* `-v {AbsolutePathToJsonFile}:/data/db.json` should be passed to mount the given JSON file into the container
* `clue/json-server` the name of this docker image

### Help

You can supply any number of JSON Server arguments that will be passed through unmodified.

```bash
$ docker run -it --rm clue/json-server --help
```

### JSON source

If you mount a file to `/data/db.json` (as in the above example),
it will automatically be used as the plain JSON data source file.

A sample file could look like this:

```json
{
  "posts": [
    { "id": 1, "body": "foo" },
    { "id": 2, "body": "bar" }
  ],
  "comments": [
    { "id": 1, "body": "baz", "postId": 1 },
    { "id": 2, "body": "qux", "postId": 2 }
  ]
}
```


