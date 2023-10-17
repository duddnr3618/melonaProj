package com.fundguide.melona.member.repository;

public class memo {

    /** 이전에 모든 보드에 대한 신고값을 조회했던것 */
    String sql = "SELECT m.* " +
            "FROM member m " +
            "WHERE m.member_id IN (" +

            "SELECT ci.member_id FROM community_impeach ci " +
            "JOIN community_board cb ON ci.board_id = cb.community_board_id " +
            "GROUP BY ci.member_id HAVING COUNT(*) >= 30 UNION ALL " +

            "SELECT li.member_id FROM leader_board_impeach li " +
            "JOIN leader_board lb ON li.board_id = lb.leader_board_id " +
            "GROUP BY li.member_id HAVING COUNT(*) >= 30 UNION ALL " +

            "SELECT ni.member_id FROM normal_board_impeach ni " +
            "JOIN normal_board nb ON ni.board_id = nb.normal_board_id " +
            "GROUP BY ni.member_id HAVING COUNT(*) >= 30) " +

            "GROUP BY m.member_id " +
            "HAVING COUNT(m.member_id) >= :countSize " +
            "ORDER BY m.member_id LIMIT :limit OFFSET :offset ";

    String intersectionSql = "SELECT member_id FROM (" +
            "SELECT ci.member_id FROM community_impeach ci " +
            "JOIN community_board cb ON ci.board_id = cb.community_board_id " +
            "GROUP BY ci.member_id HAVING COUNT(*) >= 30 " +
            "UNION ALL " +

            "SELECT li.member_id FROM leader_board_impeach li " +
            "JOIN leader_board lb ON li.board_id = lb.leader_board_id " +
            "GROUP BY li.member_id HAVING COUNT(*) >= 30 " +
            "UNION ALL " +

            "SELECT ni.member_id FROM normal_board_impeach ni " +
            "JOIN normal_board nb ON ni.board_id = nb.normal_board_id " +

            ") AS subquery GROUP BY member_id HAVING COUNT(member_id) >= ?1 " +
            "ORDER BY member_id DESC";
}
