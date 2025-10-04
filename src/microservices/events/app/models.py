from pydantic import BaseModel
from typing import Optional

class MovieEvent(BaseModel):
    movie_id: int
    title: str
    action: str
    user_id: int

class UserEvent(BaseModel):
    user_id: int
    username: str
    action: str
    timestamp: str

class PaymentEvent(BaseModel):
    payment_id: int
    user_id: int
    amount: float
    status: str
    timestamp: str
    method_type: str