const state = {
  pageTitle: ''
}

const getters = {
  pageTitle: state => state.pageTitle
}

const actions = {
  setPageTitle({ commit }, { pageTitle }) {
    commit('setPageTitle', { pageTitle })
  }
}

const mutations = {
  setPageTitle (state, { pageTitle }) {
    state.pageTitle = pageTitle
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}