# 🔐 أكواد تسجيل الدخول بجوجل - كاملة وجاهزة للاستخدام

## 📋 المحتويات
1. كود HTML لصفحة تسجيل الدخول
2. كود JavaScript لمعالجة تسجيل الدخول
3. كود CSS للتنسيق
4. خطوات الإعداد في Google Cloud Console

---

## 1️⃣ كود HTML (أضفه في <head> و <body>)

### في قسم <head> من ملف login.html:

```html
<!-- Google Sign-In Script -->
<script src="https://accounts.google.com/gsi/client" async defer></script>
<meta name="google-signin-client_id" content="YOUR_GOOGLE_CLIENT_ID.apps.googleusercontent.com">
```

### في قسم <body> - استبدل الزر القديم بهذا الكود:

```html
<!-- Google Sign-In Button -->
<div id="g_id_onload"
     data-client_id="YOUR_GOOGLE_CLIENT_ID.apps.googleusercontent.com"
     data-context="signin"
     data-ux_mode="popup"
     data-callback="handleGoogleSignIn"
     data-auto_prompt="false">
</div>

<div class="g_id_signin google-btn-wrapper"
     data-type="standard"
     data-shape="rectangular"
     data-theme="outline"
     data-text="signin_with"
     data-size="large"
     data-locale="ar"
     data-logo_alignment="left"
     data-width="100%">
</div>

<!-- زر احتياطي مخصص بتصميم جميل -->
<button type="button" class="btn btn-google" onclick="handleCustomGoogleSignIn()">
    <svg width="18" height="18" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 48 48">
        <path fill="#EA4335" d="M24 9.5c3.54 0 6.71 1.22 9.21 3.6l6.85-6.85C35.9 2.38 30.47 0 24 0 14.62 0 6.51 5.38 2.56 13.22l7.98 6.19C12.43 13.72 17.74 9.5 24 9.5z"/>
        <path fill="#4285F4" d="M46.98 24.55c0-1.57-.15-3.09-.38-4.55H24v9.02h12.94c-.58 2.96-2.26 5.48-4.78 7.18l7.73 6c4.51-4.18 7.09-10.36 7.09-17.65z"/>
        <path fill="#FBBC05" d="M10.53 28.59c-.48-1.45-.76-2.99-.76-4.59s.27-3.14.76-4.59l-7.98-6.19C.92 16.46 0 20.12 0 24c0 3.88.92 7.54 2.56 10.78l7.97-6.19z"/>
        <path fill="#34A853" d="M24 48c6.48 0 11.93-2.13 15.89-5.81l-7.73-6c-2.15 1.45-4.92 2.3-8.16 2.3-6.26 0-11.57-4.22-13.47-9.91l-7.98 6.19C6.51 42.62 14.62 48 24 48z"/>
        <path fill="none" d="M0 0h48v48H0z"/>
    </svg>
    تسجيل الدخول بحساب جوجل
</button>
```

---

## 2️⃣ كود JavaScript الكامل

### أضف هذا الكود في بداية ملف script.js (قبل كل شيء):

```javascript
// ========== Google Sign-In Handler ==========
function handleGoogleSignIn(response) {
    try {
        // فك تشفير JWT token من Google
        const credential = response.credential;
        const payload = parseJwt(credential);
        
        // استخراج معلومات المستخدم
        const userName = payload.name;
        const userEmail = payload.email;
        const userPicture = payload.picture;
        
        console.log('تم تسجيل الدخول:', {
            name: userName,
            email: userEmail,
            picture: userPicture
        });
        
        // حفظ بيانات المستخدم في Local Storage
        localStorage.setItem('userLoggedIn', 'true');
        localStorage.setItem('userEmail', userEmail);
        localStorage.setItem('userName', userName);
        localStorage.setItem('userPicture', userPicture);
        localStorage.setItem('loginMethod', 'google');
        
        // عرض رسالة نجاح
        alert('✅ تم تسجيل الدخول بنجاح عبر Google!\nمرحباً ' + userName);
        
        // الانتقال إلى لوحة التحكم بعد ثانية
        setTimeout(() => {
            window.location.href = 'dashboard.html';
        }, 1000);
        
    } catch (error) {
        console.error('❌ خطأ في تسجيل الدخول بجوجل:', error);
        alert('حدث خطأ أثناء تسجيل الدخول. الرجاء المحاولة مرة أخرى.');
    }
}

// دالة لفك تشفير JWT token من Google
function parseJwt(token) {
    try {
        // فصل الـ token إلى 3 أجزاء
        const base64Url = token.split('.')[1];
        // استبدال الرموز الخاصة
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        // فك التشفير
        const jsonPayload = decodeURIComponent(
            atob(base64).split('').map(function(c) {
                return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
            }).join('')
        );
        // تحويل إلى Object
        return JSON.parse(jsonPayload);
    } catch (error) {
        console.error('❌ خطأ في فك تشفير الـ token:', error);
        return null;
    }
}

// دالة للزر المخصص
function handleCustomGoogleSignIn() {
    try {
        // التحقق من توفر Google API
        if (window.google && window.google.accounts && window.google.accounts.id) {
            // فتح نافذة تسجيل الدخول
            window.google.accounts.id.prompt((notification) => {
                if (notification.isNotDisplayed() || notification.isSkippedMoment()) {
                    console.log('⚠️ تم إلغاء تسجيل الدخول');
                }
            });
        } else {
            // رسالة توجيهية للإعداد
            alert(
                '📋 لتفعيل تسجيل الدخول بجوجل:\n\n' +
                '1️⃣ احصل على Google Client ID\n' +
                '2️⃣ استبدل YOUR_GOOGLE_CLIENT_ID\n' +
                '3️⃣ ارفع الموقع على خادم\n\n' +
                '💡 للاختبار: استخدم تسجيل الدخول العادي'
            );
        }
    } catch (error) {
        console.error('❌ خطأ:', error);
        alert('الرجاء استخدام تسجيل الدخول العادي');
    }
}
```

### كود إضافي لعرض صورة المستخدم في لوحة التحكم:

```javascript
// أضف هذا الكود في ملف script.js في قسم DOMContentLoaded

// عرض صورة المستخدم من Google في لوحة التحكم
const isLoggedIn = localStorage.getItem('userLoggedIn') === 'true';
const currentPage = window.location.pathname.split('/').pop();

if (isLoggedIn && currentPage === 'dashboard.html') {
    const userName = localStorage.getItem('userName') || 'المستخدم';
    const userPicture = localStorage.getItem('userPicture');
    
    // تحديث الترحيب
    const welcomeText = document.querySelector('.dashboard-header h1');
    if (welcomeText) {
        welcomeText.textContent = `مرحباً، ${userName.split(' ')[0]} 👋`;
    }
    
    // عرض صورة الملف الشخصي
    if (userPicture) {
        const avatarCircle = document.querySelector('.avatar-circle');
        if (avatarCircle) {
            avatarCircle.innerHTML = `
                <img src="${userPicture}" 
                     alt="صورة الملف الشخصي" 
                     style="width: 100%; height: 100%; border-radius: 50%; object-fit: cover;">
            `;
        }
        
        // تحديث القائمة العلوية
        const userMenuBtn = document.querySelector('#userMenuBtn span');
        if (userMenuBtn) {
            userMenuBtn.innerHTML = `
                <img src="${userPicture}" 
                     alt="Profile" 
                     style="width: 30px; height: 30px; border-radius: 50%; 
                            object-fit: cover; margin-left: 8px;">
                ${userName.split(' ')[0]}
            `;
        }
    }
}
```

---

## 3️⃣ كود CSS للتنسيق

### أضف هذا في ملف style.css:

```css
/* تنسيق زر Google Sign-In */
.btn-google {
    background-color: #fff;
    color: #333;
    border: 2px solid #ddd;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 10px;
    font-weight: 600;
    transition: all 0.3s ease;
    padding: 12px 30px;
    border-radius: 25px;
    cursor: pointer;
    width: 100%;
    margin-top: 10px;
}

.btn-google:hover {
    border-color: #4285F4;
    box-shadow: 0 2px 8px rgba(66, 133, 244, 0.3);
    transform: translateY(-2px);
}

.btn-google svg {
    flex-shrink: 0;
}

/* تنسيق زر Google الرسمي */
.google-btn-wrapper {
    width: 100%;
    display: flex;
    justify-content: center;
    margin-bottom: 15px;
}

.g_id_signin {
    width: 100% !important;
}

.g_id_signin > div {
    margin: 0 auto !important;
}

/* تنسيق الفاصل */
.divider {
    text-align: center;
    margin: 25px 0;
    position: relative;
    color: #999;
}

.divider::before,
.divider::after {
    content: "";
    position: absolute;
    top: 50%;
    width: 40%;
    height: 1px;
    background-color: #ddd;
}

.divider::before {
    right: 0;
}

.divider::after {
    left: 0;
}
```

---

## 4️⃣ خطوات الحصول على Google Client ID

### الخطوة 1: إنشاء مشروع في Google Cloud Console

1. اذهب إلى: https://console.cloud.google.com/
2. سجل دخولك بحساب Google
3. انقر على "Select a project" ثم "NEW PROJECT"
4. اسم المشروع: **أكاديمية البرمجة**
5. انقر CREATE

### الخطوة 2: تفعيل Google Sign-In API

1. من القائمة الجانبية: **APIs & Services** → **Library**
2. ابحث عن: **Google+ API**
3. انقر **ENABLE**

### الخطوة 3: إعداد OAuth Consent Screen

1. من القائمة: **APIs & Services** → **OAuth consent screen**
2. اختر: **External**
3. املأ البيانات:
   - App name: **أكاديمية البرمجة**
   - User support email: بريدك الإلكتروني
   - Developer contact: بريدك الإلكتروني
4. انقر **SAVE AND CONTINUE**
5. في Scopes: انقر **SAVE AND CONTINUE** (اتركها كما هي)
6. في Test users: أضف بريدك الإلكتروني
7. انقر **SAVE AND CONTINUE**

### الخطوة 4: إنشاء OAuth 2.0 Client ID

1. من القائمة: **APIs & Services** → **Credentials**
2. انقر: **+ CREATE CREDENTIALS** → **OAuth client ID**
3. اختر: **Web application**
4. اسم: **Web Client**
5. في **Authorized JavaScript origins** أضف:
   ```
   http://localhost
   http://127.0.0.1
   http://localhost:8000
   https://yourwebsite.com
   ```
6. في **Authorized redirect URIs** أضف:
   ```
   http://localhost/login.html
   https://yourwebsite.com/login.html
   ```
7. انقر **CREATE**

### الخطوة 5: نسخ Client ID

1. ستظهر نافذة بها **Client ID**
2. انسخ الـ Client ID (مثال):
   ```
   123456789-abcdefg.apps.googleusercontent.com
   ```

### الخطوة 6: استبدال Client ID في الكود

استبدل `YOUR_GOOGLE_CLIENT_ID` في المكانين التاليين في ملف login.html:

```html
<!-- المكان الأول في <head> -->
<meta name="google-signin-client_id" content="123456789-abcdefg.apps.googleusercontent.com">

<!-- المكان الثاني في <body> -->
<div id="g_id_onload"
     data-client_id="123456789-abcdefg.apps.googleusercontent.com"
     ...>
</div>
```

---

## 5️⃣ اختبار تسجيل الدخول

### ⚠️ مهم جداً:
تسجيل الدخول بجوجل **لا يعمل** من ملف محلي مباشر (file:///)

### طرق التشغيل الصحيحة:

#### أ) استخدام Live Server في VS Code:
1. افتح المشروع في VS Code
2. انقر بالزر الأيمن على index.html
3. اختر "Open with Live Server"
4. سيفتح على: http://localhost:5500

#### ب) استخدام Python:
```bash
# Python 3
python -m http.server 8000

# ثم افتح: http://localhost:8000
```

#### ج) استخدام XAMPP:
1. ضع الملفات في: C:/xampp/htdocs/academy
2. شغل Apache
3. افتح: http://localhost/academy

#### د) رفع على استضافة:
- GitHub Pages
- Netlify
- Vercel
- أي استضافة ويب

---

## 6️⃣ اختبار التطبيق

1. افتح الموقع على http://localhost
2. اذهب إلى صفحة تسجيل الدخول
3. انقر على زر "تسجيل الدخول بحساب جوجل"
4. اختر حساب Google
5. وافق على الأذونات
6. ✅ سيتم تسجيل دخولك تلقائياً!

---

## 7️⃣ معلومات تُحفظ من Google

عند تسجيل الدخول، يتم حفظ:

```javascript
{
    userLoggedIn: "true",
    userEmail: "user@gmail.com",
    userName: "اسم المستخدم الكامل",
    userPicture: "https://lh3.googleusercontent.com/a/...",
    loginMethod: "google"
}
```

---

## 8️⃣ استكشاف الأخطاء

### خطأ: "Invalid Client"
✅ تأكد من نسخ Client ID بشكل صحيح

### خطأ: "redirect_uri_mismatch"
✅ أضف الرابط الصحيح في Google Console

### خطأ: الزر لا يظهر
✅ تأكد من رفع الموقع على خادم (ليس file:///)

### خطأ: "Access blocked"
✅ أضف بريدك في Test users في Google Console

---

## 9️⃣ الكود الكامل في ملف واحد (للنسخ السريع)

### login.html (القسم المهم فقط):

```html
<!DOCTYPE html>
<html lang="ar" dir="rtl">
<head>
    <meta charset="UTF-8">
    <title>تسجيل الدخول</title>
    
    <!-- Google Sign-In -->
    <script src="https://accounts.google.com/gsi/client" async defer></script>
    <meta name="google-signin-client_id" content="YOUR_CLIENT_ID.apps.googleusercontent.com">
    
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <!-- نموذج تسجيل الدخول -->
    <form id="loginForm">
        <!-- حقول الإدخال العادية هنا -->
        
        <button type="submit">تسجيل الدخول</button>
        
        <div class="divider"><span>أو</span></div>
        
        <!-- زر Google الرسمي -->
        <div id="g_id_onload"
             data-client_id="YOUR_CLIENT_ID.apps.googleusercontent.com"
             data-callback="handleGoogleSignIn"
             data-auto_prompt="false">
        </div>
        
        <div class="g_id_signin"
             data-type="standard"
             data-size="large"
             data-locale="ar">
        </div>
    </form>
    
    <script src="script.js"></script>
</body>
</html>
```

---

## 🎉 انتهى!

الآن لديك جميع الأكواد الكاملة لتسجيل الدخول بجوجل!

### ملخص سريع:
1. ✅ احصل على Client ID من Google Cloud Console
2. ✅ انسخ أكواد HTML و JavaScript و CSS
3. ✅ استبدل YOUR_CLIENT_ID بالـ ID الخاص بك
4. ✅ ارفع الموقع على خادم
5. ✅ جرب تسجيل الدخول!

---

💡 **نصيحة**: احفظ Client ID و Client Secret في مكان آمن!
