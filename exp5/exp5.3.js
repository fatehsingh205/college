import express from "express";
import mongoose from "mongoose";
import productRoutes from "./routes/productRoutes.js";

const app = express();
app.use(express.json());

mongoose.connect("mongodb://127.0.0.1:27017/ecommerce", {
  useNewUrlParser: true,
  useUnifiedTopology: true
}).then(() => console.log("MongoDB connected"))
  .catch(err => console.error(err));

app.use("/products", productRoutes);

app.listen(3000, () => console.log(" Server running on port 3000"));
