# Import necessary libraries
import pandas as pd
import numpy as np
from sklearn.preprocessing import StandardScaler
from sklearn.ensemble import RandomForestClassifier
import joblib

# Load the datasets (Training/Testing)
train_data = pd.read_csv(r'train.csv')
test_data = pd.read_csv(r'test.csv') 

# Display the first few rows of the dataset
print(train_data.head())

# Data Preprocessing
X_train = train_data.drop(['price_range'], axis=1)  
y_train = train_data['price_range']

X_test = test_data.drop(['id'], axis=1)  

# Scaling the features
scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

# Training a Random Forest Classifier
model = RandomForestClassifier(n_estimators=100, random_state=42)
model.fit(X_train_scaled, y_train)

# Saving the model and scaler
joblib.dump(model, 'device_price_model.pkl')
joblib.dump(scaler, 'scaler.pkl')

# Making predictions on test data
y_pred_test = model.predict(X_test_scaled)

# Saving predictions 
np.save('predictions.npy', y_pred_test)

print("Model trained and predictions saved successfully.")
