async function fetchCsrfToken() {
    const response = await fetch("/api/csrf");

    if (!response.ok) {
        throw new Error("Could not obtain CSRF token");
    }

    return response.json();
}