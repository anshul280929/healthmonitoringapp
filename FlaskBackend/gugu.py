from kivy.app import App
from kivy.uix.label import Label
from kivy.uix.boxlayout import BoxLayout
import requests
import threading
import time

def get_address_from_coordinates(latitude, longitude, api_key):
    url = f"https://api.opencagedata.com/geocode/v1/json?q={latitude},{longitude}&key={api_key}"
    response = requests.get(url)
    if response.status_code == 200:
        data = response.json()
        if "results" in data and len(data["results"]) > 0:
            return data["results"][0]["formatted"]
        else:
            return "No address found for the given coordinates."
    else:
        return f"Error: Unable to fetch data (HTTP {response.status_code})"

def get_directions(source_address, destination_address, google_api_key):
    base_url = "https://maps.googleapis.com/maps/api/directions/json"
    params = {
        "origin": source_address,
        "destination": destination_address,
        "key": google_api_key,
        "mode": "walking",
        "units": "metric"
    }
    response = requests.get(base_url, params=params)
    print("Directions API Response:", response.json())
    if response.status_code == 200:
        directions_data = response.json()
        if "routes" in directions_data and directions_data["routes"]:
            steps = directions_data["routes"][0]["legs"][0]["steps"]
            return steps
    return []

class GPSApp(App):
    def update_gps_data(self, gps_label, open_cage_api_key, google_api_key):
        source_latitude = "12.9477852"
        source_longitude = "77.4800651"

        esp_url = "http://192.168.137.1:8080/directions"

        source_address = get_address_from_coordinates(source_latitude, source_longitude, open_cage_api_key)

        while True:
            try:
                response = requests.get("http://192.168.137.1:5000/gps")
                destination_data = response.json()

                destination_latitude = destination_data.get('latitude', 'N/A')
                destination_longitude = destination_data.get('longitude', 'N/A')

                if destination_latitude != 'N/A' and destination_longitude != 'N/A':
                    destination_address = get_address_from_coordinates(
                        destination_latitude, destination_longitude, open_cage_api_key
                    )
                else:
                    destination_address = "Invalid destination coordinates"

                if "Invalid" not in destination_address and "Error" not in destination_address:
                    steps, travel_time = get_directions(source_address, destination_address, google_api_key)
                    directions_text = "\n".join(steps)
                else:
                    directions_text = "Unable to fetch directions."
                    travel_time = "N/A"

                gps_text = (f"Source Address:\n{source_address}\n\n"
                            f"Destination Address:\n{destination_address}\n\n"
                            f"Directions:\n{directions_text}\n\n"
                            f"Estimated Travel Time: {travel_time} minutes")
                gps_label.text = gps_text

                if directions_text != "Unable to fetch directions.":
                    payload = {
                        "directions": directions_text,
                        "travel_time": travel_time
                    }
                    esp_response = requests.post(esp_url, json=payload)
                    print("Directions sent to ESP:", esp_response.text)
                else:
                    print("No valid directions to send to ESP.")

            except Exception as e:
                gps_label.text = f"Error: {e}"
            time.sleep(5)

    def build(self):
        open_cage_api_key = ""
        google_api_key = ""

        layout = BoxLayout(orientation='vertical')
        gps_label = Label(text="Fetching GPS Data...", font_size=20)
        layout.add_widget(gps_label)
        
        threading.Thread(target=self.update_gps_data, args=(gps_label, open_cage_api_key, google_api_key), daemon=True).start()
        return layout

if __name__ == '__main__':
    GPSApp().run()
