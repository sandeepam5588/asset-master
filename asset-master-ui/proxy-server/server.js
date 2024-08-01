const http = require("http");

const app = require("./app");

const http_server = http.createServer(app);

http_server.listen(3300, () => {
  console.log("http server running on port 3300");
});
