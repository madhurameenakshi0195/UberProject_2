from fastapi import FastAPI
from pydantic import BaseModel
import pandas as pd
import joblib

# Load the trained model
model = joblib.load("fare_model.pkl")
driver_model = joblib.load("driver_model.pkl")

app = FastAPI()


class FareRequest(BaseModel):
    distance: float
    duration: float
class DriverInput(BaseModel):
    id: int
    distance: float
    rating: float
    completedRides: int
    acceptanceRate: float


class DriverRequest(BaseModel):
    drivers: list[DriverInput]

@app.get("/")
def home():
    return {"message": "Uber AI Fare Prediction Service Running"}


@app.post("/predict-fare")
def predict_fare(request: FareRequest):

    input_data = pd.DataFrame(
        [[request.distance, request.duration]],
        columns=["distance", "duration"]
    )

    predicted_fare = model.predict(input_data)[0]

    return {
        "fare": round(float(predicted_fare), 2)
    }
@app.post("/match-driver")
def match_driver(request: DriverRequest):

    best_driver = None
    best_score = -1

    for driver in request.drivers:

        features = pd.DataFrame([[
            driver.distance,
            driver.rating,
            driver.completedRides,
            driver.acceptanceRate
        ]], columns=[
            "distance",
            "rating",
            "completedRides",
            "acceptanceRate"
        ])

        score = driver_model.predict(features)[0]

        if score > best_score:
            best_score = score
            best_driver = driver.id

    return {
        "driverId": best_driver
    }