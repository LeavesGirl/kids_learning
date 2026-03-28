import{a}from"./axios-8531ffad.js";const s={list:t=>a.get("/math/listData",{params:t}),detail:t=>a.get(`/math/detail/${t}`),generate:t=>a.post("/math/generate",t)};export{s as m};
