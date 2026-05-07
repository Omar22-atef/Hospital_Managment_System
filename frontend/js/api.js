const BASE_URL = "http://localhost:8080";

/**
 * Wrapper for the Fetch API to automatically include tokens, handle base URL,
 * and parse the response data structure.
 */
async function apiFetch(endpoint, options = {}) {
    const headers = {
        "Content-Type": "application/json",
        ...options.headers
    };

    const token = localStorage.getItem("token");
    if (token) {
        headers["Authorization"] = `Bearer ${token}`;
    }

    const config = {
        ...options,
        headers
    };

    try {
        const response = await fetch(`${BASE_URL}${endpoint}`, config);

        const raw = await response.text();

        let data;
        try {
            data = raw ? JSON.parse(raw) : null;
        } catch {
            data = raw;
        }

        if (!response.ok) {
            const errorMessage =
                data?.message || data || `HTTP error! status: ${response.status}`;
            throw new Error(errorMessage);
        }

        if (data && typeof data === "object" && data.hasOwnProperty("data")) {
            return data.data;
        }

        return data;

    } catch (error) {
        console.error("API Fetch Error:", error);
        throw error;
    }
}
