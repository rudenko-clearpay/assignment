export const transfer = async (request) => {
    return fetch(`http://localhost:8080/transfer`,
        {
            method: "POST",
            body: JSON.stringify(request),
            headers: {'Content-Type': 'application/json'}
        })
        .then(async response => {
            const data = await response.json();
            return {
                status: response.status,
                data: data
            }
        });
}