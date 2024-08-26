/**
 * 盲盒中小，包含MysteryBox，MysteryBoxProductRel，MysteryBoxOrder，MysteryBoxOrderItem，其中MysteryBox是聚合根因此root就代表MysteryBox。
 * 管理员先创建盲盒（MysteryBox），然后添加盲盒商品（MysteryBoxProductRel）。
 * 用户在小程序端浏览盲盒（MysteryBox），点击盲盒可以查看盲盒的详情（MysteryBox join MysteryProductRel join Product）
 * 然后创建盲盒订单（MysteryBox），一个订单内可能包含多个盲盒（MysteryBoxOrderItem）
 */
package io.github.qifan777.server.box;
