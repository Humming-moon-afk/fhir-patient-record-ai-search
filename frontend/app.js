

const patientForm = document.getElementById('PatientForm');

const emergencyColor = document.getElementById('Notfallstufe')

const Sinput = document.getElementById('Search-input')

const Sbutton = document.getElementById('Search-button')

const Sresult = document.getElementById('Search-results')

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


Sbutton.addEventListener('click', function(event) {
    const query = Sinput.value;
    console.log(query);

    Sresult.innerHTML='<p style="font-size: 12px;">Suche läuft...</p>';
    
    fetch('http://localhost:8080/api/patients/search?q=' + encodeURIComponent(query), {
       method: 'GET'

    })
    .then (response => {
        if(!response.ok) throw new Error('Server issue');
        return response.json();
    })
    .then (data => {
        Sresult.innerHTML = '';

        if(!data || data.length === 0) {
            Sresult.innerHTML = '<p style="font-size: 12px; color: #555;">Keine Patienten gefunden.</p>';
            return;
        }
        data.forEach(patient => {
            const card = document.createElement('div');
            card.classList.add('patient-card');
            card.innerHTML = `
                <h3>${patient.firstName} ${patient.lastName}</h3>
                <p><strong>Klinik:</strong> ${patient.managingClinic}</p>
                <p><strong>Puls:</strong> ${patient.puls || patient.Puls || '-'} | <strong>Blutdruck:</strong> ${patient.blutdruck || patient.Blutdruck || '-'}</p>
                <p><strong>Notfallstufe:</strong> ${patient.notfallstufe || patient.Notfallstufe || 'Normal'}</p>
                <p><strong>Symptome:</strong> ${patient.sonstiges || '-'}</p>
            `;
            Sresult.appendChild(card);
        });
    })
    .catch(error => {
        console.error('Fehler: ', error);
        Sresult.innerHTML = '<p style="font-size: 12px; color: red;">Verbindung zum Server fehlgeschlagen.</p>';
    });
});
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
        patientForm.reset();
        updateEmergencyColor();
    })
    .catch(error => {
        console.error('Issue: ', error);
    });
});