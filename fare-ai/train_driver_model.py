import pandas as pd
from sklearn.ensemble import RandomForestRegressor
import joblib

data = pd.read_csv("driver_data.csv")

X = data[[
    "distance",
    "rating",
    "completedRides",
    "acceptanceRate"
]]

y = data["score"]

model = RandomForestRegressor(
    n_estimators=100,
    random_state=42
)

model.fit(X, y)

joblib.dump(model, "driver_model.pkl")

print("Driver model trained successfully!")