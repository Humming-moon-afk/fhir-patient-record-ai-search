

const patientForm = document.getElementById('PatientForm');

const emergencyColor = document.getElementById('Notfallstufe')

const Sinput = document.getElementById('Search-input')

const Sbutton = document.getElementById('Search-button')

const Sresult = document.getElementById('Search-results')

Sbutton.addEventListener('click', function(event) {
    const query = Sinput.value;
    console.log(query);

    fetch('http://localhost:8080/api/patients/search?q=' + encodeURIComponent(query), {
    method: 'GET'
    })
    .then(response => response.json())
    .then(data => {
       const card = document.createElement('div');
    card.innerHTML = `
    <h3>${patient.firstName} ${patient.lastName}</h3>
    <p>Klinik: ${patient.managingClinic}</p>
    <p>Puls: ${patient.puls || patient.Puls} | Blutdruck: ${patient.blutdruck || patient.Blutdruck}</p>
    <p>Notfallstufe: ${patient.notfallstufe || patient.Notfallstufe}</p>
    <p>Symptome: ${patient.sonstiges}</p>
`;
Sresult.appendChild(card);
    })
    .catch(error => console.error('Fehler: ', error))
});


function updateEmergencyColor() {
   if(emergencyColor.value == 'Normal') {
    emergencyColor.style.background = 'green'
   }
   else if(emergencyColor.value == 'Dringend') {
    emergencyColor.style.background = 'yellow'
   }
   else {
    emergencyColor.style.background = 'red'
   }
}
updateEmergencyColor();
emergencyColor.addEventListener('change', function(event) {
   updateEmergencyColor();
})

patientForm.addEventListener('submit', function(event) {
    event.preventDefault();

    const formData = new FormData(patientForm);
    const dataObject = Object.fromEntries(formData);

    const patientJSON = JSON.stringify(dataObject, null, 2);

    
    fetch('http://localhost:8080/api/patients', {
        method: 'POST',
        headers: {
            'Content-Type' : 'application/json'
        },
        body: patientJSON
    })
    .then(response => response.text())
    .then(data => {
        console.log('Server-Antwort', data);
        alert('Patient erfolgreich an das Backend übermittelt!');
    })
    .catch(error => {
        console.error('Fehler beim Übertragen:', error);
    });
});

