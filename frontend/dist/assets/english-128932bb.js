import{a as e}from"./axios-8531ffad.js";const s={list:t=>e.get("/english/listData",{params:t}),detail:t=>e.get(`/english/detail/${t}`),update:(t,a)=>e.put(`/english/update/${t}`,a)};export{s as e};
