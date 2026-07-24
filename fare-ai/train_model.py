import pandas as pd
from sklearn.linear_model import LinearRegression
import joblib

# Load dataset
data = pd.read_csv("sample_data.csv")

# Features
X = data[["distance", "duration"]]

# Target
y = data["fare"]

# Train model
model = LinearRegression()
model.fit(X, y)

# Save trained model
joblib.dump(model, "fare_model.pkl")


def print(param):
    pass


print("✅ Model trained successfully!")