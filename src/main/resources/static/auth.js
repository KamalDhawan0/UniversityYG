const API = window.AppConfig.API_URL;
let isLogin = true;

// DOM Elements
const formTitle = document.getElementById('formTitle');
const authBtn = document.getElementById('authBtn');
const authForm = document.getElementById('authForm');
const togglePrompt = document.getElementById('togglePrompt');

// Update Form Mode
function updateFormMode() {
  formTitle.textContent = isLogin ? 'Login' : 'Register';
  authBtn.textContent = isLogin ? 'Login' : 'Register';

  togglePrompt.innerHTML = isLogin
    ? `Don’t have an account? <a href="#" id="toggleLink">Register Instead</a>`
    : `Already registered? <a href="#" id="toggleLink">Back to Login</a>`;

  attachToggleHandler(); // re-attach after innerHTML update
}

// Loader helper functions
function showLoader() {
  document.getElementById('globalLoader').style.display = 'flex';
}
function hideLoader() {
  document.getElementById('globalLoader').style.display = 'none';
}

// Attach handler to dynamic link
function attachToggleHandler() {
  const toggleLink = document.getElementById('toggleLink');
  toggleLink.addEventListener('click', (e) => {
    e.preventDefault();
    isLogin = !isLogin;
    updateFormMode();
    authForm.reset();
  });
}

// Handle submit
authForm.addEventListener('submit', async (e) => {
  e.preventDefault();

  const username = document.getElementById('username').value.trim();
  const password = document.getElementById('password').value.trim();
  const endpoint = isLogin ? 'login' : 'register';

  showLoader();  // <--- SHOW the loader before async begins
  try {
    const res = await fetch(`${API}/auth/${endpoint}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username, password }),
    });

    let data = {};
    try {
      data = await res.json(); // safely parse JSON only if response is expected to have body
    } catch (err) {
      console.warn("Failed to parse JSON:", err);
    }

    if (res.ok) {
      if (isLogin) {
        localStorage.setItem('token', data.token);
        window.location.href = 'university.html';
      } else {
        alert('Registration successful! Please login.');
        isLogin = true;
        updateFormMode();
        authForm.reset();
      }
    } else {
      if (isLogin && data.message?.toLowerCase().includes("not found")) {
        alert("User not found. Redirecting to register...");
        isLogin = false;
        updateFormMode();
      } else {
        alert(data.message || "Something went wrong.");
      }
    }
  } catch (err) {
    alert("Server error. Please try again later.");
    console.error(err);
  } finally {
    hideLoader(); // <--- HIDE the loader in finally (always runs)
  }
});

// Initialize form state
updateFormMode();
