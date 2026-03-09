package com.spring.agendadortarefas.business;

import com.spring.agendadortarefas.business.dto.TarefasDTO;
import com.spring.agendadortarefas.business.mapper.TarefasConverter;
import com.spring.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.spring.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.spring.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.spring.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefa(String token, TarefasDTO dto) {

        String email = jwtUtil.extrairEmailToken(token.substring(7));

        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);

        TarefasEntity entity = tarefasConverter.paraTarefaEntity(dto);

        return tarefasConverter.paraTarefaDTO(tarefasRepository.save(entity));
    }
}
