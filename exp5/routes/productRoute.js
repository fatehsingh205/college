import express from "express";
import Product from "./models/product2.js";

const router = express.Router();

// 🟢Get all products
router.get("/", async (req, res) => {
  const products = await Product.find();
  res.status(200).json(products);
});

//  Get products by category
router.get("/category/:category", async (req, res) => {
  const products = await Product.find({ category: req.params.category });
  res.status(200).json(products);
});

//  Get products by variant color
router.get("/by-color/:color", async (req, res) => {
  const color = req.params.color;
  const products = await Product.find({ "variants.color": color });
  res.status(200).json(products);
});

export default router;
