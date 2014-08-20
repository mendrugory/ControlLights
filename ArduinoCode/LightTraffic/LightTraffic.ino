#define  PIN4          4
#define  PIN7          7
#define  PIN8          8
#define  BUFFER_SIZE  32

char buffer[BUFFER_SIZE];

//functions
int readSerialPort();
void cleanBuffer();
void controlLights();
boolean operatingLights();
int comparisonReception();
void switchOffAll();
boolean sameString(char* buf1, char* buf2,int size2);

void setup() 
{
  // initialize both serial ports:
  Serial.begin(115200);
  cleanBuffer();
  
  //initalize pins
  pinMode(PIN4,OUTPUT);
  pinMode(PIN7,OUTPUT);
  pinMode(PIN8,OUTPUT);
  presentation();
  
  //Welcome
  Serial.println("Welcome to Arduino Traffic Light");
  delay(10);
}

void loop() 
{

  if (Serial.available()) 
    controlLights();
}

void cleanBuffer()
{
  for(int i = 0; i<BUFFER_SIZE; i++)
  {
    buffer[i] = '\0';
  }
}

void presentation()
{
  digitalWrite(PIN7,HIGH);
  delay(500);
  digitalWrite(PIN7,LOW);
  
  digitalWrite(PIN8,HIGH);
  delay(500);
  digitalWrite(PIN8,LOW);
  
  digitalWrite(PIN4,HIGH);
  delay(500);
  digitalWrite(PIN4,LOW);
}

int readSerialPort()
{
  int i = 0;
  while(Serial.available())
  {
    buffer[i++] = Serial.read();
    delay(1);
  }
  buffer[i] = '\0';
  return (i-1);
}

void switchOffAll()
{
  digitalWrite(PIN4,LOW);
  digitalWrite(PIN7,LOW);
  digitalWrite(PIN8,LOW);
}

boolean sameString(char* buf1, char* buf2,int size2)
{
  boolean ok = true;
  int i = 0;
  while(i<size2 && ok)
  {
    if(buf1[i] != buf2[i])
    {
      ok = false;
    }
    i++;
  }
  return ok;
}


int comparisonReception()
{
  char RED[4] = "red";
  char GREEN[6] = "green";
  char YELLOW[7] = "yellow";

  if(sameString(buffer,RED,4))
  {
    return 1;
  }
  
  if(sameString(buffer,YELLOW,7))
  {
    return 2;
  }
  
  if(sameString(buffer,GREEN,6))
  {
    return 3;
  }
  
  return 0;
}

boolean operatingLights()
{
  int comparison = comparisonReception();
  switch(comparison)
  {
    case 1:
      switchOffAll();
      digitalWrite(PIN4,HIGH);
      break;
    
    case 2:
      switchOffAll();
      digitalWrite(PIN8,HIGH);
      break;
      
    case 3:
      switchOffAll();
      digitalWrite(PIN7,HIGH);
      break;
      
    default:
      Serial.println("Wrong command.");
  }
}

void controlLights()
{
  cleanBuffer();
  readSerialPort();
  Serial.print("Received command: ");
  Serial.println(buffer);
  operatingLights();
}
