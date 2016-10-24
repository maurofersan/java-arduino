const int PLAY_PIN = 10;
const int PAUSE_PIN = 5;
const int QUIT_PIN = 4;

int lastAnalogValue = -1;

void setup() {
  Serial.end();
  pinMode(PLAY_PIN,INPUT);  
  Serial.begin(9600);
}

void loop() {
  int playValue = digitalRead(PLAY_PIN);
  if (playValue == 1) {
    Serial.println("PLAY_PLS|1");
  }

  int pauseValue = digitalRead(PAUSE_PIN);
  if (pauseValue != 0) {
    Serial.print("PAUSE_PLS|");
    Serial.println(pauseValue);
  }

  int quitValue = digitalRead(QUIT_PIN);
  if (quitValue != 0) {
    Serial.print("QUIT_PLS|");
    Serial.println(quitValue);
  }
  
  int analogValue = analogRead(A0);
  if (lastAnalogValue == -1 || abs(analogValue - lastAnalogValue) > 10) {
    lastAnalogValue = analogValue;
    Serial.print("MOV_PTN|");
    Serial.println(analogValue);
  }
  delay(100);
}
