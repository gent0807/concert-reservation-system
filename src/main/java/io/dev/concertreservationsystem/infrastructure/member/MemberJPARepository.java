package io.dev.concertreservationsystem.infrastructure.member;

import io.dev.concertreservationsystem.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJPARepository extends JpaRepository<Member, Long> {
}
