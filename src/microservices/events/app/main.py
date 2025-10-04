from fastapi import FastAPI, HTTPException
from contextlib import asynccontextmanager
from app.kafka_producer import get_kafka_producer
from app.kafka_consumer import start_consumer_in_background
from app.models import MovieEvent, UserEvent, PaymentEvent
from loguru import logger

@asynccontextmanager
async def lifespan(app: FastAPI):
    start_consumer_in_background()
    yield

app = FastAPI(lifespan=lifespan)

@app.post("/api/events/movie", status_code=201)
async def send_movie_event(event: MovieEvent):
    try:
        producer = get_kafka_producer()
        producer.send("movie-events", event.dict())  # ← дефис!
        producer.flush()
        logger.info("Sent movie event to 'movie-events': {}", event.dict())
        return {"status": "success"}
    except Exception as e:
        logger.exception("Failed to send movie event")
        raise HTTPException(status_code=500, detail="Kafka send failed")

@app.post("/api/events/user", status_code=201)
async def send_user_event(event: UserEvent):
    try:
        producer = get_kafka_producer()
        producer.send("user-events", event.dict())  # ← дефис!
        producer.flush()
        logger.info("Sent user event to 'user-events': {}", event.dict())
        return {"status": "success"}
    except Exception as e:
        logger.exception("Failed to send user event")
        raise HTTPException(status_code=500, detail="Kafka send failed")

@app.post("/api/events/payment", status_code=201)
async def send_payment_event(event: PaymentEvent):
    try:
        producer = get_kafka_producer()
        producer.send("payment-events", event.dict())  # ← дефис!
        producer.flush()
        logger.info("Sent payment event to 'payment-events': {}", event.dict())
        return {"status": "success"}
    except Exception as e:
        logger.exception("Failed to send payment event")
        raise HTTPException(status_code=500, detail="Kafka send failed")

@app.get("/api/events/health")
async def health_check():
    return {"status": True}