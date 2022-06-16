import { reactive } from 'vue'

export const store = reactive({
  user: null,
  token: null,
  setUser(user) {
    this.user = user;
    sessionStorage.setItem('user', user)
  },
  setToken(token) {
    this.token = token;
    sessionStorage.setItem('jwttoken', token)
  },
  logout() {
    this.token = null;
    this.user = null;
    sessionStorage.removeItem('jwttoken');
  },
  getUser() {
    this.user ??= sessionStorage.getItem('user')
    return this.user;
  },
  getToken() {
    this.token ??= sessionStorage.getItem('jwttoken')
    return this.token;
  }
})