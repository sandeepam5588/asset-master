const express = require("express");
const axios = require("axios");
const express_useragent = require("express-useragent");
const cors = require("cors");
const morgan = require("morgan");

const app = express();
app.use(morgan("dev"));

app.use(cors(["http://127.0.0.1:51836"]));

// BODY PARSER
app.use(express.json());
app.use(express_useragent.express());

app.use("/", async (req, res, next) => {
  const method = req.method;
  const url = req.url;
  const body = req.body;

  console.log(req.headers["user-agent"]);

  try {
    const response = await axios({
      method,
      url: `http://127.0.0.1:8088${url}`,
      data: body,
      // headers: {
      //   "user-agent": "PostmanRuntime/7.33.0",
      // },
    });

    res.status(response.status).json(response.data);
  } catch (error) {
    console.log(error.response.status, error.response.data);
    // console.log(error.status, error.data);

    res.status(error.response.status).json(error.response.data);
  }
});

module.exports = app;
