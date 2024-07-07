from flask import Flask, request, jsonify
import joblib
import numpy as np

# Load the trained model and scaler
model = joblib.load('device_price_model.pkl')
scaler = joblib.load('scaler.pkl')

app = Flask(__name__)

@app.route('/predict', methods=['POST'])
def predict():
    data = request.json
    features = np.array([data['battery_power'], data['blue'], data['clock_speed'], data['dual_sim'],
                         data['fc'], data['four_g'], data['int_memory'], data['m_dep'], data['mobile_wt'],
                         data['n_cores'], data['pc'], data['px_height'], data['px_width'], data['ram'],
                         data['sc_h'], data['sc_w'], data['talk_time'], data['three_g'], data['touch_screen'], data['wifi']])
    
    features = features.reshape(1, -1)
    features = scaler.transform(features)
    prediction = model.predict(features)
    
    return jsonify({'price_range': int(prediction[0])})

if __name__ == '__main__':
    app.run(debug=True)