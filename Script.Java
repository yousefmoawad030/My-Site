// ========== Login/Registration Forms Toggle ==========
document.addEventListener('DOMContentLoaded', function() {
    const showRegisterBtn = document.getElementById('showRegister');
    const showLoginBtn = document.getElementById('showLogin');
    const loginForm = document.getElementById('loginForm');
    const registerForm = document.getElementById('registerForm');

    if (showRegisterBtn) {
        showRegisterBtn.addEventListener('click', function(e) {
            e.preventDefault();
            loginForm.style.display = 'none';
            registerForm.style.display = 'block';
        });
    }

    if (showLoginBtn) {
        showLoginBtn.addEventListener('click', function(e) {
            e.preventDefault();
            loginForm.style.display = 'block';
            registerForm.style.display = 'none';
        });
    }

    // ========== Login Form Submission ==========
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;

            // Simple validation
            if (email && password) {
                // Store user data (in real app, this would be done on server)
                localStorage.setItem('userLoggedIn', 'true');
                localStorage.setItem('userEmail', email);
                localStorage.setItem('userName', 'أحمد محمد');
                
                // Show success message
                alert('تم تسجيل الدخول بنجاح!');
                
                // Redirect to dashboard
                window.location.href = 'dashboard.html';
            } else {
                alert('الرجاء إدخال البريد الإلكتروني وكلمة المرور');
            }
        });
    }

    // ========== Registration Form Submission ==========
    if (registerForm) {
        registerForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const name = document.getElementById('reg-name').value;
            const email = document.getElementById('reg-email').value;
            const password = document.getElementById('reg-password').value;
            const confirmPassword = document.getElementById('reg-confirm-password').value;

            // Validate passwords match
            if (password !== confirmPassword) {
                alert('كلمات المرور غير متطابقة');
                return;
            }

            // Simple validation
            if (name && email && password) {
                // Store user data
                localStorage.setItem('userLoggedIn', 'true');
                localStorage.setItem('userEmail', email);
                localStorage.setItem('userName', name);
                
                // Show success message
                alert('تم إنشاء الحساب بنجاح!');
                
                // Redirect to dashboard
                window.location.href = 'dashboard.html';
            } else {
                alert('الرجاء ملء جميع الحقول');
            }
        });
    }

    // ========== Contact Form Submission ==========
    const contactForm = document.getElementById('contactForm');
    if (contactForm) {
        contactForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            const firstName = document.getElementById('firstName').value;
            const lastName = document.getElementById('lastName').value;
            const email = document.getElementById('contactEmail').value;
            const subject = document.getElementById('subject').value;
            const message = document.getElementById('message').value;

            if (firstName && lastName && email && subject && message) {
                alert('شكراً لك! تم إرسال رسالتك بنجاح. سنتواصل معك قريباً.');
                contactForm.reset();
            } else {
                alert('الرجاء ملء جميع الحقول المطلوبة');
            }
        });
    }

    // ========== Dashboard Navigation ==========
    const menuItems = document.querySelectorAll('.menu-item');
    const sections = document.querySelectorAll('.dashboard-section');

    menuItems.forEach(item => {
        item.addEventListener('click', function(e) {
            e.preventDefault();
            
            // Remove active class from all menu items
            menuItems.forEach(mi => mi.classList.remove('active'));
            
            // Add active class to clicked item
            this.classList.add('active');
            
            // Hide all sections
            sections.forEach(section => section.classList.remove('active'));
            
            // Show selected section
            const sectionId = this.getAttribute('data-section');
            const targetSection = document.getElementById(sectionId);
            if (targetSection) {
                targetSection.classList.add('active');
            }
        });
    });

    // ========== Course Tabs ==========
    const tabBtns = document.querySelectorAll('.tab-btn');
    const tabContents = document.querySelectorAll('.tab-content');

    tabBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            // Remove active class from all buttons
            tabBtns.forEach(b => b.classList.remove('active'));
            
            // Add active class to clicked button
            this.classList.add('active');
            
            // Hide all tab contents
            tabContents.forEach(content => content.classList.remove('active'));
            
            // Show selected tab content
            const tabId = this.getAttribute('data-tab');
            const targetTab = document.getElementById(tabId);
            if (targetTab) {
                targetTab.classList.add('active');
            }
        });
    });

    // ========== User Menu Dropdown ==========
    const userMenuBtn = document.getElementById('userMenuBtn');
    const userDropdown = document.getElementById('userDropdown');

    if (userMenuBtn && userDropdown) {
        userMenuBtn.addEventListener('click', function(e) {
            e.preventDefault();
            userDropdown.classList.toggle('show');
        });

        // Close dropdown when clicking outside
        document.addEventListener('click', function(e) {
            if (!userMenuBtn.contains(e.target) && !userDropdown.contains(e.target)) {
                userDropdown.classList.remove('show');
            }
        });
    }

    // ========== Logout Functionality ==========
    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', function(e) {
            e.preventDefault();
            
            // Clear user data
            localStorage.removeItem('userLoggedIn');
            localStorage.removeItem('userEmail');
            localStorage.removeItem('userName');
            
            // Show message
            alert('تم تسجيل الخروج بنجاح');
            
            // Redirect to home
            window.location.href = 'index.html';
        });
    }

    // ========== Check if User is Logged In ==========
    const isLoggedIn = localStorage.getItem('userLoggedIn') === 'true';
    const currentPage = window.location.pathname.split('/').pop();

    // Redirect to login if trying to access dashboard without login
    if (currentPage === 'dashboard.html' && !isLoggedIn) {
        alert('الرجاء تسجيل الدخول أولاً');
        window.location.href = 'login.html';
    }

    // Update user name in dashboard if logged in
    if (isLoggedIn && currentPage === 'dashboard.html') {
        const userName = localStorage.getItem('userName') || 'المستخدم';
        const welcomeText = document.querySelector('.dashboard-header h1');
        if (welcomeText && welcomeText.textContent.includes('مرحباً')) {
            welcomeText.textContent = `مرحباً، ${userName.split(' ')[0]} 👋`;
        }
    }

    // ========== Course Filtering ==========
    const levelFilter = document.getElementById('levelFilter');
    const categoryFilter = document.getElementById('categoryFilter');
    const sortFilter = document.getElementById('sortFilter');

    function filterCourses() {
        const courseCards = document.querySelectorAll('.course-card');
        const levelValue = levelFilter ? levelFilter.value : 'all';
        const categoryValue = categoryFilter ? categoryFilter.value : 'all';

        courseCards.forEach(card => {
            const cardLevel = card.getAttribute('data-level');
            const cardCategory = card.getAttribute('data-category');
            
            let showCard = true;

            if (levelValue !== 'all' && cardLevel !== levelValue) {
                showCard = false;
            }

            if (categoryValue !== 'all' && cardCategory !== categoryValue) {
                showCard = false;
            }

            if (showCard) {
                card.style.display = 'block';
                setTimeout(() => {
                    card.style.opacity = '1';
                    card.style.transform = 'scale(1)';
                }, 10);
            } else {
                card.style.opacity = '0';
                card.style.transform = 'scale(0.9)';
                setTimeout(() => {
                    card.style.display = 'none';
                }, 300);
            }
        });
    }

    if (levelFilter) {
        levelFilter.addEventListener('change', filterCourses);
    }

    if (categoryFilter) {
        categoryFilter.addEventListener('change', filterCourses);
    }

    // ========== Smooth Scroll for Anchor Links ==========
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            const href = this.getAttribute('href');
            if (href !== '#' && href !== '') {
                const target = document.querySelector(href);
                if (target) {
                    e.preventDefault();
                    target.scrollIntoView({
                        behavior: 'smooth',
                        block: 'start'
                    });
                }
            }
        });
    });

    // ========== Add Animation on Scroll ==========
    const observerOptions = {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    };

    const observer = new IntersectionObserver(function(entries) {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.style.opacity = '1';
                entry.target.style.transform = 'translateY(0)';
            }
        });
    }, observerOptions);

    // Observe elements for animation
    const animatedElements = document.querySelectorAll('.feature-card, .course-card, .value-card, .team-member');
    animatedElements.forEach(el => {
        el.style.opacity = '0';
        el.style.transform = 'translateY(20px)';
        el.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
        observer.observe(el);
    });

    // ========== Profile Form Submission ==========
    const profileForm = document.querySelector('.profile-form');
    if (profileForm) {
        profileForm.addEventListener('submit', function(e) {
            e.preventDefault();
            alert('تم حفظ التغييرات بنجاح!');
        });
    }

    // ========== Settings Form Updates ==========
    const settingsSwitches = document.querySelectorAll('.switch-input');
    settingsSwitches.forEach(switchInput => {
        switchInput.addEventListener('change', function() {
            const label = this.closest('.switch-label').querySelector('span').textContent;
            const status = this.checked ? 'مفعّل' : 'معطّل';
            console.log(`${label}: ${status}`);
        });
    });

    // ========== Mobile Menu Toggle ==========
    const createMobileMenu = () => {
        const navbar = document.querySelector('.navbar');
        const navLinks = document.querySelector('.nav-links');
        
        if (window.innerWidth <= 768 && !document.querySelector('.mobile-menu-btn')) {
            const menuBtn = document.createElement('button');
            menuBtn.className = 'mobile-menu-btn';
            menuBtn.innerHTML = '☰';
            menuBtn.style.cssText = 'font-size: 24px; background: none; border: none; cursor: pointer; display: block;';
            
            navbar.querySelector('.container').insertBefore(menuBtn, navLinks);
            
            menuBtn.addEventListener('click', () => {
                navLinks.classList.toggle('mobile-active');
                navLinks.style.display = navLinks.classList.contains('mobile-active') ? 'flex' : 'none';
            });
        }
    };

    createMobileMenu();
    window.addEventListener('resize', createMobileMenu);

    // ========== Show Loading Animation ==========
    const showLoading = (element) => {
        element.style.opacity = '0.5';
        element.style.pointerEvents = 'none';
    };

    const hideLoading = (element) => {
        element.style.opacity = '1';
        element.style.pointerEvents = 'auto';
    };

    // ========== Progress Bar Animation ==========
    const progressBars = document.querySelectorAll('.progress-fill');
    progressBars.forEach(bar => {
        const width = bar.style.width;
        bar.style.width = '0';
        setTimeout(() => {
            bar.style.width = width;
        }, 100);
    });

    // ========== Form Validation Helper ==========
    const validateEmail = (email) => {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    };

    const validatePhone = (phone) => {
        const re = /^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/;
        return re.test(phone);
    };

    // ========== Auto-save Draft Feature ==========
    const autoSaveForms = document.querySelectorAll('form[data-autosave]');
    autoSaveForms.forEach(form => {
        const inputs = form.querySelectorAll('input, textarea');
        inputs.forEach(input => {
            // Load saved data
            const savedValue = localStorage.getItem(`draft_${input.name}`);
            if (savedValue) {
                input.value = savedValue;
            }

            // Save on input
            input.addEventListener('input', () => {
                localStorage.setItem(`draft_${input.name}`, input.value);
            });
        });

        // Clear draft on submit
        form.addEventListener('submit', () => {
            inputs.forEach(input => {
                localStorage.removeItem(`draft_${input.name}`);
            });
        });
    });

    // ========== Course Search Functionality ==========
    const searchInput = document.getElementById('courseSearch');
    if (searchInput) {
        searchInput.addEventListener('input', function(e) {
            const searchTerm = e.target.value.toLowerCase();
            const courseCards = document.querySelectorAll('.course-card');

            courseCards.forEach(card => {
                const title = card.querySelector('h3').textContent.toLowerCase();
                const description = card.querySelector('p').textContent.toLowerCase();

                if (title.includes(searchTerm) || description.includes(searchTerm)) {
                    card.style.display = 'block';
                } else {
                    card.style.display = 'none';
                }
            });
        });
    }

    console.log('أكاديمية البرمجة - تم تحميل الموقع بنجاح!');
});

// ========== Utility Functions ==========
function showNotification(message, type = 'success') {
    const notification = document.createElement('div');
    notification.className = `notification notification-${type}`;
    notification.textContent = message;
    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 15px 25px;
        background: ${type === 'success' ? '#27AE60' : '#E74C3C'};
        color: white;
        border-radius: 5px;
        box-shadow: 0 2px 10px rgba(0,0,0,0.2);
        z-index: 10000;
        animation: slideIn 0.3s ease;
    `;

    document.body.appendChild(notification);

    setTimeout(() => {
        notification.style.animation = 'slideOut 0.3s ease';
        setTimeout(() => {
            notification.remove();
        }, 300);
    }, 3000);
}

// Add CSS for notifications
const style = document.createElement('style');
style.textContent = `
    @keyframes slideIn {
        from {
            transform: translateX(100%);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }

    @keyframes slideOut {
        from {
            transform: translateX(0);
            opacity: 1;
        }
        to {
            transform: translateX(100%);
            opacity: 0;
        }
    }
`;
document.head.appendChild(style);