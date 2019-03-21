#include <FastLED.h>

// How many leds in your strip?
#define NUM_LEDS 64
#define DATA_PIN 7
#define CLOCK_PIN 13 
#define FRAMES_PER_SECOND 60
bool gReverseDirection = false;
CRGB leds[NUM_LEDS];
int analogPin = 0;

void setup() {
 Serial.begin(57600);
  Serial.println("resetting");
  LEDS.addLeds < WS2811, DATA_PIN, GRB > (leds, NUM_LEDS);
  LEDS.setBrightness(84);
  Serial.begin(9600);
}

void loop() {
  int analogValue = analogRead(analogPin);  
  Serial.println(analogValue);
  
  if (analogValue < 100) {     // Startup (Voltage is at 0)
    for (int i = 0; i < NUM_LEDS; i++) {
    leds[i] = CRGB::Black;    
    }
    FastLED.show();
    delay(1000);
    for (int i = 0; i < NUM_LEDS; i++) {
      leds[i] = CRGB::Red;
    }
    FastLED.show();  
    delay(1000);
  }



  if (analogValue > 100 && analogValue < 300) {     // Zeroed but not connected (Voltage is at 1)
    for (int i = 0; i < NUM_LEDS; i++) {
    leds[i] = CRGB::Black;    
    }
    FastLED.show();
    delay(1000);
    for (int i = 0; i < NUM_LEDS; i++) {
      leds[i] = CRGB::Blue;
    }
    FastLED.show();  
    delay(1000);
  }
  
  
  
  
  if (analogValue > 300 && analogValue < 500) {             // Connected/Passive State (Voltage is at 2 volts)
    static uint8_t hue = 0;
    //Serial.print("x");
    // First slide the led in one direction
    for (int i = 0; i < NUM_LEDS; i++) {
      FastLED.show();
      leds[i] = CRGB::Orange;
      delay(10);
    }
    //Serial.print("x");
    // Now go in the other direction.  
    for (int i = (NUM_LEDS) - 1; i >= 0; i--) {
      FastLED.show();
      leds[i] = CRGB::Blue;
      delay(10);
    }
  }

  if (analogValue > 500 && analogValue < 700) {             // Intake Position State (Voltage is at 3 volts)
    static uint8_t hue = 0;
    //Serial.print("x");
    // First slide the led in one direction
    for (int i = 0; i < NUM_LEDS; i++) {
      FastLED.show();
      leds[i] = CRGB::White;
      delay(10);
    }
    //Serial.print("x");
    // Now go in the other direction.  
    for (int i = (NUM_LEDS) - 1; i >= 0; i--) {
      FastLED.show();
      leds[i] = CRGB::Blue;
      delay(10);
    }
  }
  
  
  
  if (analogValue > 700 && analogValue < 900) {     // When holding a game object (Voltage is at 4 volts)
    for (int i = 0; i < NUM_LEDS; i++) {
    leds[i] = CRGB::Black;    
  }
  FastLED.show();
  delay(250);
  for (int i = 0; i < NUM_LEDS; i++) {
    leds[i] = CRGB::White;
  }
  FastLED.show();  
  delay(250);
  }




  if (analogValue > 900) {                 //When climbing (voltage is at 5 volts)
    Fire2012(); // run simulation frame
  
  FastLED.show(); // display this frame
  FastLED.delay(1000 / FRAMES_PER_SECOND);
  }



  
}





// COOLING: How much does the air cool as it rises?
// Less cooling = taller flames.  More cooling = shorter flames.
// Default 50, suggested range 20-100 
#define COOLING  55

// SPARKING: What chance (out of 255) is there that a new spark will be lit?
// Higher chance = more roaring fire.  Lower chance = more flickery fire.
// Default 120, suggested range 50-200.
#define SPARKING 120
void Fire2012()
{
// Array of temperature readings at each simulation cell
  static byte heat[NUM_LEDS];

  // Step 1.  Cool down every cell a little
    for( int i = 0; i < NUM_LEDS; i++) {
      heat[i] = qsub8( heat[i],  random8(0, ((COOLING * 10) / NUM_LEDS) + 2));
    }
  
    // Step 2.  Heat from each cell drifts 'up' and diffuses a little
    for( int k= NUM_LEDS - 1; k >= 2; k--) {
      heat[k] = (heat[k - 1] + heat[k - 2] + heat[k - 2] ) / 3;
    }
    
    // Step 3.  Randomly ignite new 'sparks' of heat near the bottom
    if( random8() < SPARKING ) {
      int y = random8(7);
      heat[y] = qadd8( heat[y], random8(160,255) );
    }

    // Step 4.  Map from heat cells to LED colors
    for( int j = 0; j < NUM_LEDS; j++) {
      CRGB color = HeatColor( heat[j]);
      int pixelnumber;
      if( gReverseDirection ) {
        pixelnumber = (NUM_LEDS-1) - j;
      } else {
        pixelnumber = j;
      }
      leds[pixelnumber] = color;
    }
}
