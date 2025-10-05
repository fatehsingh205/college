import express from "express";
const app = express();
const PORT = 3000;

app.use(express.json());

// In-memory seat data
let seats = [
  { id: 1, status: "available", lockedBy: null, lockExpiry: null },
  { id: 2, status: "available", lockedBy: null, lockExpiry: null },
  { id: 3, status: "available", lockedBy: null, lockExpiry: null },
  { id: 4, status: "available", lockedBy: null, lockExpiry: null }
];

// Utility: check and release expired locks
function releaseExpiredLocks() {
  const now = Date.now();
  seats.forEach(seat => {
    if (seat.status === "locked" && seat.lockExpiry && now > seat.lockExpiry) {
      seat.status = "available";
      seat.lockedBy = null;
      seat.lockExpiry = null;
    }
  });
}

// GET all seats
app.get("/seats", (req, res) => {
  releaseExpiredLocks();
  res.json(seats);
});

// Lock a seat
app.post("/lock/:seatId", (req, res) => {
  releaseExpiredLocks();

  const seatId = parseInt(req.params.seatId);
  const { userId } = req.body;

  const seat = seats.find(s => s.id === seatId);
  if (!seat) return res.status(404).json({ message: "Seat not found" });

  if (seat.status === "booked") {
    return res.status(400).json({ message: "Seat already booked" });
  }

  if (seat.status === "locked" && seat.lockedBy !== userId) {
    return res.status(400).json({ message: "Seat is currently locked by another user" });
  }

  // Lock seat
  seat.status = "locked";
  seat.lockedBy = userId;
  seat.lockExpiry = Date.now() + 60 * 1000; // 1 min lock

  return res.json({ message: `Seat ${seatId} locked for user ${userId}` });
});

// Confirm booking
app.post("/confirm/:seatId", (req, res) => {
  releaseExpiredLocks();

  const seatId = parseInt(req.params.seatId);
  const { userId } = req.body;

  const seat = seats.find(s => s.id === seatId);
  if (!seat) return res.status(404).json({ message: "Seat not found" });

  if (seat.status === "available") {
    return res.status(400).json({ message: "Seat must be locked before confirming" });
  }

  if (seat.status === "locked" && seat.lockedBy !== userId) {
    return res.status(400).json({ message: "You cannot confirm a seat locked by another user" });
  }

  // Confirm booking
  seat.status = "booked";
  seat.lockedBy = null;
  seat.lockExpiry = null;

  return res.json({ message: `Seat ${seatId} booked successfully by user ${userId}` });
});

// Start server
app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});
