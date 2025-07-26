from flask import Flask, request, jsonify
import random
import re
from gugu import get_address_from_coordinates, get_directions

app = Flask(__name__)

# Global state for GPS data
gps_data = {}
current_step_index = 0  # To track the progress of directions

# Simulated function to get heartbeat
def get_heartbeat():
    # Return a random heartbeat value between 60 and 100 bpm
    return random.randint(60, 100)

# Simulated function to get temperature
def get_temperature():
    # Return a random temperature value between 36.0 and 38.0°C
    return round(random.uniform(36.0, 38.0), 1)

@app.route('/heartbeat', methods=['GET'])
def heartbeat():
    # Return simulated heartbeat value as JSON
    return jsonify({"heartbeat": get_heartbeat()})

@app.route('/temperature', methods=['GET'])
def temperature():
    # Return simulated temperature value as JSON
    return jsonify({"temperature": get_temperature()})

# Endpoint to receive GPS data from the wearable device
@app.route('/gps', methods=['POST'])
def receive_gps():
    global gps_data
    gps_data = request.json
    print("Received GPS Data:", gps_data)
    return jsonify({"status": "success", "received": gps_data})

current_step_index = 0

# Endpoint to send turn-by-turn directions
@app.route('/gps', methods=['GET'])
def send_gps():
    global gps_data, current_step_index
    source_latitude = "12.9477852"
    source_longitude = "77.4800651"
    open_cage_api_key = ""
    google_api_key = ""

    # Convert source coordinates to address
    source_address = get_address_from_coordinates(source_latitude, source_longitude, open_cage_api_key)

    # Convert destination coordinates to address
    dest_latitude = gps_data.get("latitude", "N/A")
    dest_longitude = gps_data.get("longitude", "N/A")
    destination_address = get_address_from_coordinates(dest_latitude, dest_longitude, open_cage_api_key)

    # Fetch turn-by-turn directions
    steps = get_directions(source_address, destination_address, google_api_key)
    if not steps:
        return jsonify({"turns": [], "completed": True})  # Send empty response if no steps available

    # Prepare the next turn
    if current_step_index < len(steps):
        step = steps[current_step_index]
        instruction = re.sub(r'<[^>]+>', '', step["html_instructions"])  # Remove HTML tags
        distance = step["distance"]["text"]
        duration = step["duration"]["text"]

        # Include current step in response
        gps_data["turns"] = [{
            "instruction": instruction,
            "distance": distance,
            "duration": duration
        }]

        # Increment the step index
        current_step_index += 1
        gps_data["completed"] = False  # Not yet completed
    else:
        gps_data["turns"] = []
        gps_data["completed"] = True  # Mark journey as completed

    return jsonify(gps_data)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
