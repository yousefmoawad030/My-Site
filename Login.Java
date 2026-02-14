function handleGoogleSignIn(response) {
    const payload = parseJwt(response.credential);
    const userName = payload.name;
    const userEmail = payload.email;
    const userPicture = payload.picture;
    // حفظ البيانات وتسجيل الدخول
}
