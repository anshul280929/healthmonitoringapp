from flask import Flask, request, jsonify
import os
from groq import Groq

app = Flask(__name__)

# Set your GROQ_API_KEY
api_key = "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"
os.environ["GROQ_API_KEY"] = api_key

client = Groq(api_key=api_key)

def generate_content(prompt):
    response = client.chat.completions.create(
        messages=[
            {
                "role": "user",
                "content": prompt
            }
        ],
        model="llama-3.1-8b-instant"
    )
    return response['choices'][0]['message']['content']

@app.route('/analyze', methods=['POST'])
def analyze():
    data = request.json
    age = data.get("age")
    gender = data.get("gender")
    heartbeat = data.get("heartbeat")
    temperature = data.get("temperature")

    prompt = f"Analyze health data: Age={age}, Gender={gender}, Heartbeat={heartbeat}, Temperature={temperature}."
    analysis = generate_content(prompt)
    return jsonify({"analysis": analysis})

if __name__ == '__main__':
    app.run(debug=True)
