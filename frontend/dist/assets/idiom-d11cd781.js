import{a as t}from"./axios-8531ffad.js";const d={list:i=>t.get("/idiom/listData",{params:i}),detail:i=>t.get(`/idiom/detail/${i}`),update:(i,a)=>t.put(`/idiom/update/${i}`,a)};export{d as i};
