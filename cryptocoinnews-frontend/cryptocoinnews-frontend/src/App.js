import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from 'axios';

function testFunction() {
  // alert("you pressed the button")
  const d = document.getElementById("cryptoSchedule");
  let formDataObj = "";
  d.addEventListener("submit", (e) => {
  e.preventDefault();

  const myFormData = new FormData(e.target);

  formDataObj = Object.fromEntries(myFormData.entries());
  console.log("making call to backend!")
  return axios.post("http://localhost:8080/schedule", formDataObj);
  });
}

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <p> Hello and welcome CryptoCoinNews app! </p>
        <p>Get the top 10 crypto coins from coinmarketcap.com to your email, sign up below: </p>
      </header>
      <body>
      <form id="cryptoSchedule">
      <div className='Form'>
      <label for="firstName">First name:</label>
      <input type="text" minlength="4" id="firstName" name="firstName" required/><br/>
      
      <label for="lastName">Last name:</label>
      <input type="text" minlength="4" id="lastName"  name="lastName" required/><br/>

      <label for="email">Email:</label>
      <input type="email" minlength="4" id="email" name="email" required/><br/>
      <br/>
      </div>
      
      <div className='Radio'>
      <input type="radio" name="cron" value="0/15 * * * * *"></input>
      <label>Send a report every 15 minutes</label>
      <br/>

      <input type="radio" name="cron" value="0 0 * ? * *"></input>
      <label>Send a report every 1 hour</label>
      <br/>

      <input type="radio" name="cron" value="0 0 0 * * ?"></input>
      <label>Send a report every 24 hours</label>
      <br/>

      <input type="radio" name="cron" value="0 0 10 ? * MON"></input>
      <label>Send a report every monday at 10am</label>
      <br/>
      </div>
      
      <input type="submit" value="Submit" onClick={testFunction}></input>
      </form>
      </body>
    </div>
  );
}

export default App;
