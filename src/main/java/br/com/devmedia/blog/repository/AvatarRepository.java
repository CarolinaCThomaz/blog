package br.com.devmedia.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.devmedia.blog.entity.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

}
