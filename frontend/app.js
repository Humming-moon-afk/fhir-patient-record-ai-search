

const patientForm = document.getElementById('PatientForm');

const emergencyColor = document.getElementById('Notfallstufe')
emergencyColor.addEventListener('change', function(event) {
   if(emergencyColor.value == 'Normal') {
    emergencyColor.style.background = 'green'
   }
   else if(emergencyColor.value == 'Dringend') {
    emergencyColor.style.background = 'yellow'
   }
   else {
    emergencyColor.style.background = 'red'
   }
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

