from flask import Flask, request, jsonify
import re
from gugu import get_address_from_coordinates, get_directions
import random
from flask_cors import CORS
from groq import Groq
import os

app = Flask(__name__)
CORS(app)


# Set your GROQ_API_KEY
api_key = ""
os.environ["GROQ_API_KEY"] = api_key

client = Groq(api_key=api_key)

def generate_content(prompt):
    try:
        # Ensure you're using the correct Groq method
        response = client.chat.completions.create(
            model="llama-3.1-8b-instant",
            messages=[{"role": "user", "content": prompt}]
        )
        print("Response received:", response)  # Debugging

        # Safely access the 'choices' attribute
        if hasattr(response, "choices") and len(response.choices) > 0:
            content = response.choices[0].message.content
            return content
        else:
            raise ValueError("Unexpected response format: missing 'choices'.")
    except Exception as e:
        print("Error generating content:", e)
        raise e  # Re-raise the error for Flask to handle


@app.route('/analyze', methods=['GET', 'POST'])
def analyze():
    try:
        print("Received request:", request.method)  # Debugging line
        
        if request.method == 'GET':
            data = {"heartbeat": 85, "temperature": 37.5}
            print("Using default data for GET:", data)  # Debugging line
        else:  # POST request
            data = request.json
            print("Received POST data:", data)  # Debugging line
        
        # Validate incoming data
        if not data or 'heartbeat' not in data or 'temperature' not in data:
            print("Validation error: Missing fields")  # Debugging line
            return jsonify({"error": "Missing required fields: heartbeat and temperature."}), 400

        # Static values for age and gender
        age = 60
        gender = "Female"
        heartbeat = data.get("heartbeat")
        temperature = data.get("temperature")

        print(f"Analyzing: Age={age}, Gender={gender}, Heartbeat={heartbeat}, Temperature={temperature}")

        # Generate analysis using Groq API
        prompt = f"Analyze health data: Age={age}, Gender={gender}, Heartbeat={heartbeat}, Temperature={temperature}."
        analysis = generate_content(prompt)
        print("Analysis result:", analysis)  # Debugging line

        return jsonify({"analysis": analysis})

    except Exception as e:
        print("Error occurred:", e)  # Debugging line
        return jsonify({"error": f"Error during analysis: {str(e)}"}), 500



def get_heartbeat():
    return random.randint(80,90)  # Random heartbeat value

def get_temperature():
    return round(random.uniform(18.0,22.0), 1)  # Random temperature value

# Endpoint to get health data
@app.route('/health_data', methods=['GET'])
def health_data():
    # Prepare health data as JSON
    health_data = {
        "heartbeat": get_heartbeat(),
        "temperature": get_temperature()
    }
    return jsonify(health_data)

# Store received data
gps_data = {}
heart_rate_data = None
temperature_data = None

# Global state for tracking navigation progress
current_step_index = 0

# Endpoint to receive heart rate data
# Endpoint to get heart rate data
@app.route('/heart_rate', methods=['GET','POST'])
def get_heart_rate():
    # Return a random heartbeat value in JSON
    global heart_rate_data
    global temperature_data

    heart_rate_data = {
        "heartbeat": get_heartbeat()
    }
    return jsonify(heart_rate_data)

# Endpoint to get temperature data
@app.route('/temperature', methods=['GET','POST'])
def get_temperature_data():
    global heart_rate_data
    global temperature_data

    # Return a random temperature value in JSON
    temperature_data = {
        "temperature": get_temperature()
    }
    return jsonify(temperature_data)


@app.route('/gps',methods=['GET','POST'])
def send_gps():
    global heart_rate_data
    global temperature_data

    gps_data = {
            "turns": {
                "latitude": 12.919650,
                "longitude": 77.564400,
                "Location": 'JSS'
            },
            "completed": True
        }
    return jsonify(gps_data)



if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
