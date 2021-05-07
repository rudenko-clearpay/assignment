
export const loadUsers = async (page) => {
    const pageNumber = page ? page.number : 0;
    return fetch(`http://localhost:8080/users?page=${pageNumber}`)
        .then(response => response.json())
        .then(response => {
            return {
                data: response._embedded.users,
                page: response.page
            }
        });
}