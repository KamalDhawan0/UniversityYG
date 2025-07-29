document.addEventListener('DOMContentLoaded', () => {
    // --- DOM Element Selections ---
    const universityModalEl = document.getElementById('universityModal');
    const universityModal = new bootstrap.Modal(universityModalEl);
    const addUniversityBtn = document.getElementById('addUniversityBtn');
    const cancelBtn = document.getElementById('cancelBtn');
    const universityForm = document.getElementById('universityForm');
    const universityTableBody = document.getElementById('universityTableBody');
    const noUniversityMsg = document.getElementById('noUniversityMsg');
    const logoutBtn = document.getElementById('logoutBtn');
    const universityViewModalEl = document.getElementById('universityViewModal');
    const universityViewModal = new bootstrap.Modal(universityViewModalEl);
    const universityViewContent = document.getElementById('universityViewContent');


    // Loader state to handle multiple concurrent requests
    let loaderCount = 0;

    function showLoader() {
        loaderCount++;
        document.getElementById('globalLoader').style.display = 'flex';
    }
    function hideLoader() {
        loaderCount = Math.max(0, loaderCount - 1);
        if (loaderCount === 0) {
            document.getElementById('globalLoader').style.display = 'none';
        }
    }


    /**
     * Returns HTTP headers for authenticated requests.
     * Automatically includes 'Authorization: Bearer <token>' if available,
     * and merges any extra headers you need (e.g. Content-Type).
     *
     * Usage:
     *   fetch(url, { headers: getAuthHeaders() });
     *   fetch(url, { headers: getAuthHeaders({'Content-Type': 'application/json'}) });
     */
    function getAuthHeaders(extraHeaders = {}) {
        // Retrieve the token stored during login (see your auth.js)
        const token = localStorage.getItem('token');

        // Compose headers:
        // - Start with anything from extraHeaders (if supplied)
        // - Add Authorization if token exists
        return {
            ...extraHeaders,
            ...(token ? { 'Authorization': 'Bearer ' + token } : {})
        };
    }

    //for fetching university ownership details
    let fullUniversityList = [];

    async function fetchFullUniversityList() {
        showLoader();
        try{
        const resp = await fetch(`${API_BASE_URL}/names/full/universities`, { headers: getAuthHeaders() });
        if (!resp.ok) throw new Error("Failed to load full university data");
        fullUniversityList = await resp.json();
        } finally{
            hideLoader();
        }
    }


    function getOwnershipTypeByName(name) {
        const uni = fullUniversityList.find(u => u.universityName === name);
        return uni ? uni.ownershipType : '';
    }



    // --- State and Choices.js Instances ---
    let universities = [];
    let universityChoices, courseChoices, cityChoices;

    // --- API Configuration ---
    const API_BASE_URL = window.AppConfig.API_URL;

    //All UniversityData
    async function fetchAllUniversityDetails() {
        showLoader();
        try {
            const response = await fetch(`${API_BASE_URL}/universities`, { headers: getAuthHeaders() });
            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
            universities = await response.json();
            renderTable();
        } catch (error) {
            console.error("Failed to fetch university details:", error);
            noUniversityMsg.textContent = "Failed to load data. Is the backend running?";
            noUniversityMsg.style.display = 'block';
            universityTableBody.innerHTML = '';
            document.querySelector('table').style.display = 'none';
        }finally{
            hideLoader();
        }
    }

    //Dropdown UPI
    async function fetchAndPopulateDropdown(endpoint, choicesInstance) {
        showLoader();
        try {
            const response = await fetch(`${API_BASE_URL}${endpoint}`, { headers: getAuthHeaders() });
            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
            const data = await response.json();
            const choices = data.map(name => ({ value: name, label: name }));
            choicesInstance.setChoices(choices, 'value', 'label', true);
        } catch (error) {
            console.error(`Failed to fetch data for ${endpoint}:`, error);
        }finally{
            hideLoader();
        }
    }


    //Add University
    async function createUniversity(data) {
        showLoader();
        try{
        const response = await fetch(`${API_BASE_URL}/universities`, {
            method: 'POST',
            headers: getAuthHeaders({ 'Content-Type': 'application/json' }),
            body: JSON.stringify(data)
        });
        if (!response.ok) throw new Error('Failed to create university');
        return response.json();
      } finally{
        hideLoader();
      }
    }

    //Update Functionality
    async function updateUniversity(id, data) {
        showLoader();
        try{
        const response = await fetch(`${API_BASE_URL}/universities/${id}`, {
            method: 'PUT',
            headers: getAuthHeaders({ 'Content-Type': 'application/json' }),
            body: JSON.stringify(data)
        });
        if (!response.ok) throw new Error('Failed to update university');
        return response.json();
        }finally{
            hideLoader();
        }
    }

    //Delete University
    async function deleteUniversity(id) {
        showLoader();
        try{
        const response = await fetch(`${API_BASE_URL}/universities/${id}`, {
            method: 'DELETE',
            headers: getAuthHeaders({ 'Content-Type': 'application/json' })
        });
        if (!response.ok) throw new Error('Failed to delete university');
        }finally{
            hideLoader();
        }
    }

    // --- UI/Component Functions ---
    function renderTable() {
        universityTableBody.innerHTML = '';
        if (universities.length === 0) {
            noUniversityMsg.style.display = 'block';
            document.querySelector('table').style.display = 'none';
        } else {
            noUniversityMsg.style.display = 'none';
            document.querySelector('table').style.display = 'table';
            universities.forEach(uni => {
                const row = document.createElement('tr');
                row.dataset.id = uni.universityId;
                row.innerHTML = `
                    <td>${uni.universityName}</td>
                    <td>${uni.courseName}</td>
                    <td>${uni.city}</td>
                    <td class="actions">
                        <button class="edit-btn btn btn-sm btn-primary" data-id="${uni.universityId}">Edit</button>
                        <button class="delete-btn btn btn-sm btn-danger" data-id="${uni.universityId}">Delete</button>
                    </td>
                `;
                universityTableBody.appendChild(row);
            });
        }
    }

    function populateForm(uni) {
        universityForm.reset();
        document.getElementById('universityId').value = uni.universityId || '';
        setTimeout(() => {
            if (universityChoices) universityChoices.setChoiceByValue(String(uni.universityName || ''));
            if (courseChoices) courseChoices.setChoiceByValue(String(uni.courseName || ''));
            if (cityChoices) cityChoices.setChoiceByValue(String(uni.city || ''));
        }, 100);

        // Populate all other fields
        document.getElementById('academicLevel').value = uni.academicLevel || '';
        document.getElementById('specialisation').value = uni.specialisation || '';
        document.getElementById('cgpa').value = uni.cgpa || '';
        document.getElementById('percentage').value = uni.percentage || '';
        document.getElementById('ieltsScore').value = uni.ieltsScore || '';
        document.getElementById('toeflScore').value = uni.toeflScore || '';
        document.getElementById('pteScore').value = uni.pteScore || '';
        document.getElementById('duolingoScore').value = uni.duolingoScore || '';
        document.getElementById('tuitionFeePeriod').value = uni.tuitionFeePeriod || '';
        document.getElementById('campusLocation').value = uni.campusLocation || '';
        document.getElementById('campusAddress').value = uni.campusAddress || '';
        document.getElementById('moi').value = uni.moi || '';
        document.getElementById('programme').value = uni.programme || '';
        document.getElementById('courseLink').value = uni.courseLink || '';
        document.getElementById('durationInYears').value = uni.durationInYears || '';
        document.getElementById('applicationStartDate').value = uni.applicationStartDate || '';
        document.getElementById('firstStepDeadline').value = uni.firstStepDeadline || '';
        document.getElementById('deadLine').value = uni.deadLine || '';
        document.getElementById('applicationFees').value = uni.applicationFees || '';
        document.getElementById('tuitionFees').value = uni.tuitionFees || '';
        document.getElementById('courseType').value = uni.courseType || '';
        document.getElementById('germanProficiencyLevel').value = uni.germanProficiencyLevel || '';
        document.getElementById('greScore').value = uni.greScore || '0';
        document.getElementById('gmatScore').value = uni.gmatScore || '0';
        document.getElementById('workExperience').value = uni.workExperience ?? '';
        document.getElementById('applicationMode').value = uni.applicationMode || '';
        document.getElementById('postRequirement').value = uni.postRequirement || '';
        document.getElementById('deadlineLink').value = uni.deadlineLink || '';
        document.getElementById('loginLink').value = uni.loginLink || '';
        document.getElementById('preApplicationTest').value = uni.preApplicationTest || 'No';
        document.getElementById('additionalRequirements').value = uni.additionalRequirements || '';
    }

    function validateForm() {
        if (!universityChoices.getValue(true)) {
            alert('Please select a University Name.');
            return false;
        }
        if (!courseChoices.getValue(true)) {
            alert('Please select a Course Name.');
            return false;
        }
        if (!cityChoices.getValue(true)) {
            alert('Please select a City.');
            return false;
        }
        if (!universityForm.checkValidity()) {
            universityForm.reportValidity();
            return false;
        }
        return true;
    }

    function getFormData() {
        return {
            universityId: document.getElementById('universityId').value || undefined,
            universityName: universityChoices.getValue(true),
            courseName: courseChoices.getValue(true),
            city: cityChoices.getValue(true),
            campusLocation: document.getElementById('campusLocation').value,
            programme: document.getElementById('programme').value,
            courseType: document.getElementById('courseType').value,
            moi: document.getElementById('moi').value,
            campusAddress: document.getElementById('campusAddress').value,
            durationInYears: document.getElementById('durationInYears').value,
            applicationStartDate: document.getElementById('applicationStartDate').value,
            firstStepDeadline: document.getElementById('firstStepDeadline').value,
            deadLine: document.getElementById('deadLine').value,
            applicationFees: document.getElementById('applicationFees').value,
            tuitionFees: document.getElementById('tuitionFees').value,
            germanProficiencyLevel: document.getElementById('germanProficiencyLevel').value,
            greScore: document.getElementById('greScore').value,
            gmatScore: document.getElementById('gmatScore').value,
            workExperience: document.getElementById('workExperience').value,
            applicationMode: document.getElementById('applicationMode').value,
            postRequirement: document.getElementById('postRequirement').value,
            deadlineLink: document.getElementById('deadlineLink').value,
            loginLink: document.getElementById('loginLink').value,
            preApplicationTest: document.getElementById('preApplicationTest').value,
            additionalRequirements: document.getElementById('additionalRequirements').value,
            courseLink: document.getElementById('courseLink').value,
            ieltsScore: document.getElementById('ieltsScore').value,
            toeflScore: document.getElementById('toeflScore').value,
            pteScore: document.getElementById('pteScore').value,
            duolingoScore: document.getElementById('duolingoScore').value,
            tuitionFeePeriod: document.getElementById('tuitionFeePeriod').value,
            academicLevel: document.getElementById('academicLevel').value,
            specialisation: document.getElementById('specialisation').value,
            cgpa: document.getElementById('cgpa').value,
            percentage: document.getElementById('percentage').value
        };
    }

    async function handleFormSubmit(e) {
        e.preventDefault();
        if (!validateForm()) return;
        const formData = getFormData();
        const isEdit = !!formData.universityId;
        try {
            if (isEdit) {
                await updateUniversity(formData.universityId, formData);
            } else {
                await createUniversity(formData);
            }
            await fetchAllUniversityDetails();
            closeModal();
        } catch (err) {
            alert('Failed to save university.');
            console.error(err);
        }
    }

    function openModalForNew() {
        universityForm.reset();
        document.getElementById('universityId').value = '';
        setTimeout(() => {
            if (universityChoices) universityChoices.setChoiceByValue('');
            if (courseChoices) courseChoices.setChoiceByValue('');
            if (cityChoices) cityChoices.setChoiceByValue('');
        }, 100);
        universityModal.show();
    }

    function closeModal() {
        universityModal.hide();
    }


    function populateViewModal(uni) {
        universityViewContent.innerHTML = `   
    <div class="container-fluid">
        <div class="row">
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">University Name:</label><div class="value">${uni.universityName || ''}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Course Name:</label><div class="value">${uni.courseName || ''}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">City:</label><div class="value">${uni.city || ''}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Ownership Type:</label><div class="value">${getOwnershipTypeByName(uni.universityName)}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Campus Location:</label><div class="value">${uni.campusLocation || ''}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Programme:</label><div class="value">${uni.programme || ''}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Course Type:</label><div class="value">${uni.courseType || ''}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Medium of Instruction:</label><div class="value">${uni.moi || ''}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Campus Address:</label><div class="value">${uni.campusAddress || ''}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Duration (Years):</label><div class="value">${uni.durationInYears || ''}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Application Start Date:</label><div class="value">${uni.applicationStartDate || ''}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">First Step Deadline:</label><div class="value">${uni.firstStepDeadline || ''}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Final Deadline:</label><div class="value">${uni.deadLine || ''}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Application Fees:</label><div class="value">${uni.applicationFees || ''}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Tuition Fee Period:</label><div class="value">${uni.tuitionFeePeriod || ''}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Tuition Fees:</label><div class="value">${uni.tuitionFees || ''}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">IELTS Score:</label><div class="value">${uni.ieltsScore || 'N/A'}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">TOEFL Score:</label><div class="value">${uni.toeflScore || 'N/A'}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">PTE Score:</label><div class="value">${uni.pteScore || 'N/A'}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">DUOLINGO Score:</label><div class="value">${uni.duolingoScore || 'N/A'}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">German Proficiency:</label><div class="value">${uni.germanProficiencyLevel || ''}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">GRE Score:</label><div class="value">${uni.greScore || 'N/A'}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">GMAT Score:</label><div class="value">${uni.gmatScore || 'N/A'}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Academic Level:</label><div class="value">${uni.academicLevel}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Specialisation:</label><div class="value">${uni.specialisation || 'N/A'}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">CGPA Required:</label><div class="value">${uni.cgpa || 'N/A'}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Percentage Required:</label><div class="value">${uni.percentage || 'N/A'}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Work Experience (Years):</label><div class="value">${uni.workExperience || 'N/A'}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Application Mode:</label><div class="value">${uni.applicationMode || ''}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Pre-Application Test:</label><div class="value">${uni.preApplicationTest || ''}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Course Link:</label><div class="value"><a href="${uni.courseLink || '#'}" target="_blank">${uni.courseLink || ''}</a></div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Deadline Link:</label><div class="value"><a href="${uni.deadlineLink || '#'}" target="_blank">${uni.deadlineLink || ''}</a></div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Login Link:</label><div class="value"><a href="${uni.loginLink || '#'}" target="_blank">${uni.loginLink || ''}</a></div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Post-Application Requirements:</label><div class="value">${uni.postRequirement || 'N/A'}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Additional Requirements:</label><div class="value">${uni.additionalRequirements || 'N/A'}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Added By:</label><div class="value">${uni.addedBy?.username}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Added On:</label><div class="value">${uni.addedOn}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Updated By:</label><div class="value">${uni.updatedBy?.username}</div></div>
            <div class="col-12 col-md-6 col-lg-4 col-xl-3"><label class="custom-label">Updated On:</label><div class="value">${uni.updatedOn}</div></div>
        </div>
      </div>
        `;
    }




    // --- Event Delegation & Listeners ---
    universityTableBody.addEventListener('click', async (e) => {
        const target = e.target;
        const button = target.closest('button');
        if (button) {
            e.stopPropagation();
            const id = button.dataset.id;
            if (button.classList.contains('edit-btn')) {
                const uni = universities.find(u => u.universityId == id);
                if (uni) {
                    populateForm(uni);
                    universityModal.show();
                }
            } else if (button.classList.contains('delete-btn')) {
                if (confirm('Are you sure you want to delete this university?')) {
                    try {
                        await deleteUniversity(id);
                        await fetchAllUniversityDetails();
                    } catch (err) {
                        alert('Failed to delete university.');
                        console.error(err);
                    }
                }
            }
        } else {
            // If not clicking a button, check if a row was clicked
            const row = target.closest('tr');
            if (row && row.dataset.id) {
                showLoader();
                try {
                    const res = await fetch(`${API_BASE_URL}/universities/${row.dataset.id}`, { headers: getAuthHeaders() });
                    if (!res.ok) throw new Error('Failed to fetch university data');
                    const uni = await res.json();
                    populateViewModal(uni);
                    universityViewModal.show();
                } catch (err) {
                    alert('Failed to load university details.');
                    console.error(err);
                }finally{
                    hideLoader();   
                }
            }
        }
    });

    addUniversityBtn.addEventListener('click', openModalForNew);
    cancelBtn.addEventListener('click', closeModal);
    universityForm.addEventListener('submit', handleFormSubmit);
    logoutBtn.addEventListener('click', () => {
        if (confirm('Do you want to Logout?')) {
            localStorage.removeItem('token');
            window.location.href = 'index.html';
        }
    });

    // --- Initial Setup ---
    async function initialize() {
        // Destroy existing choices if they exist, to prevent errors on hot-reload
        if (universityChoices) universityChoices.destroy();
        if (courseChoices) courseChoices.destroy();
        if (cityChoices) cityChoices.destroy();

        // Initialize Choices.js ONCE.
        universityChoices = new Choices('#universityName');
        courseChoices = new Choices('#courseName');
        cityChoices = new Choices('#city');

        // Fetch all necessary data in parallel for efficiency.
        await Promise.all([
            fetchAllUniversityDetails(),
            fetchAndPopulateDropdown('/names/universities', universityChoices),
            fetchAndPopulateDropdown('/names/courses', courseChoices),
            fetchAndPopulateDropdown('/names/cities', cityChoices),
            fetchFullUniversityList()
        ]);
    }

    initialize();
});
