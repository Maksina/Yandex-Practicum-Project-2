from kafka import KafkaConsumer
import json
import threading
from loguru import logger
import os

KAFKA_BROKERS = os.getenv("KAFKA_BROKERS", "kafka:9092")
# ТОЧНЫЕ имена топиков из docker-compose
TOPICS = ["movie-events", "user-events", "payment-events"]

def consume_events():
    consumer = KafkaConsumer(
        *TOPICS,
        bootstrap_servers=KAFKA_BROKERS.split(','),
        auto_offset_reset='earliest',
        enable_auto_commit=True,
        group_id='events-service-group',
        value_deserializer=lambda x: json.loads(x.decode('utf-8'))
    )

    logger.info("Kafka consumer started. Listening to topics: {}", TOPICS)
    for message in consumer:
        logger.info("Consumed message from topic '{}': {}", message.topic, message.value)

def start_consumer_in_background():
    thread = threading.Thread(target=consume_events, daemon=True)
    thread.start()