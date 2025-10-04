from kafka import KafkaProducer
import json
import os

# Используем переменную из вашего docker-compose
KAFKA_BROKERS = os.getenv("KAFKA_BROKERS", "kafka:9092")

def get_kafka_producer():
    return KafkaProducer(
        bootstrap_servers=KAFKA_BROKERS.split(','),
        value_serializer=lambda v: json.dumps(v, ensure_ascii=False).encode('utf-8')
    )