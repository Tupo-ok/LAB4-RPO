import axios from 'axios'
import Utils from '../utils/Utils'
import { store, alertActions } from '../utils/Rdx';

const API_URL = 'http://localhost:8080/api'
const AUTH_URL = 'http://localhost:8080/auth'

class BackendService {

    login(login, password) {
        return axios.post(`${AUTH_URL}/login`, {login, password})
    }

    logout() {
        return axios.get(`${AUTH_URL}/logout`, {headers : {Authorization : Utils.getToken()}})
    }

    retrieveAllCountries(countries) {
        return axios.get('http://localhost:8080/api/countries', countries, {headers : {Authorization : Utils.getToken()}});
    }
    
    retrieveCountry(id) {
        return axios.get(`${API_URL}/countries/${id}`, {headers : {Authorization : Utils.getToken()}});
    }
    
    createCountry(country) {
        return axios.post(`${API_URL}/countries`, country, {headers : {Authorization : Utils.getToken()}});
    }
    
    updateCountry(country) {
        return axios.put(`${API_URL}/countries/${country.id}`, country, {headers : {Authorization : Utils.getToken()}});
    }
    
    deleteCountries(countries) {
        return axios.post(`${API_URL}/deletecountries`, countries, {headers : {Authorization : Utils.getToken()}});
    }


    retrieveAllMuseums(page, limit) {
        return axios.get(`${API_URL}/museums?page=${page}&limit=${limit}`, {headers : {Authorization : Utils.getToken()}});
    }
    
    retrieveMuseum(id) {
        return axios.get(`${API_URL}/museums/${id}`, {headers : {Authorization : Utils.getToken()}});
    }
    
    createMuseum(country) {
        return axios.post(`${API_URL}/museums`, country, {headers : {Authorization : Utils.getToken()}});
    }
    
    updateMuseums(country) {
        return axios.put(`${API_URL}/museums/${country.id}`, country, {headers : {Authorization : Utils.getToken()}});
    }
    
    deleteMuseums(countries) {
        return axios.post(`${API_URL}/deletemuseums`, countries, {headers : {Authorization : Utils.getToken()}});
    }

    retrieveAllPaints(page, limit) {
        return axios.get(`${API_URL}/paints?page=${page}&limit=${limit}`, {headers : {Authorization : Utils.getToken()}});
    }
    
    retrievePaints(id) {
        return axios.get(`${API_URL}/paints/${id}`, {headers : {Authorization : Utils.getToken()}});
    }
    
    createPaint(country) {
        return axios.post(`${API_URL}/paints`, country, {headers : {Authorization : Utils.getToken()}});
    }
    
    updatePaint(country) {
        return axios.put(`${API_URL}/paints/${country.id}`, country, {headers : {Authorization : Utils.getToken()}});
    }
    
    deletePaints(countries) {
        return axios.post(`${API_URL}/deletepaints`, countries, {headers : {Authorization : Utils.getToken()}});
    }

    retrieveAllArtists(artist) {
        return axios.get(`${API_URL}/artists`, artist, {headers : {Authorization : Utils.getToken()}});
    }
    
    retrieveArtist(id) {
        return axios.get(`${API_URL}/artists/${id}`, {headers : {Authorization : Utils.getToken()}});
    }
    
    createArtist(artist) {
        return axios.post(`${API_URL}/artists`, artist, {headers : {Authorization : Utils.getToken()}});
    }
    
    updateArtist(artist) {
        return axios.put(`${API_URL}/artists/${artist.id}`, artist, {headers : {Authorization : Utils.getToken()}});
    }
    
    deleteArtists(artists) {
        return axios.post(`${API_URL}/deletecountries`, artists, {headers : {Authorization : Utils.getToken()}});
    }

    retrieveAllUsers(page, limit) {
        return axios.get(`${API_URL}/users?page=${page}&limit=${limit}`);
    }

    retrieveUser(id) {
        return axios.get(`${API_URL}/users/${id}`);
    }

    createUser(user) {
        return axios.post(`${API_URL}/users`, user);
    }

    updateUser(user) {
        return axios.put(`${API_URL}/users/${user.id}`, user);
    }

    deleteUsers(users) {
        return axios.post(`${API_URL}/deleteusers`, users);
    }

}

function showError(msg)
{
    store.dispatch(alertActions.error(msg))
}

axios.interceptors.request.use(
    config => {
        store.dispatch(alertActions.clear())
        let token = Utils.getToken();
        if (token)
            config.headers.Authorization = token;
        return config;
    },
    error => {
        showError(error.message)
        return Promise.reject(error);
    })

axios.interceptors.response.use(undefined,
    error => {
        if (error.response && error.response.status && [401, 403].indexOf(error.response.status) !== -1)
            showError("Ошибка авторизации")
        else if (error.response && error.response.data && error.response.data.message)
            showError(error.response.data.message)
        else
            showError(error.message)
        return Promise.reject(error);
    })
        

export default new BackendService()