package br.com.example.service;

import br.com.example.projection.*;
import br.com.example.repository.ConsultaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;

    public ConsultaService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public List<RodoviaProjection> buscarRodoviasPorMunicipio(String codigo) {
        return consultaRepository.buscarRodoviasPorMunicipio(codigo);
    }

    public ExtensaoRodoviaProjection calcularExtensaoRodoviaria(String codigo){
        return consultaRepository.calcularExtensaoRodoviaria(codigo);
    }

    public List<RodoviaJuriscricaoProjection> buscarRodoviasPorJuriscricao(String codigo, String juriscricao){
        return consultaRepository.buscarRodoviasPorJuriscricao(codigo, juriscricao);
    }

    public List<FerroviaProjection> buscarFerroviasPorMunicipio(String codigo){
        return consultaRepository.buscarFerroviasPorMunicipio(codigo);
    }

    public ExtensaoFerroviariaProjection calcularExtensaoFerroviaria(String codigo){
        return consultaRepository.calcularExtensaoFerroviaria(codigo);
    }

    public List<AeroportoProximoProjection> buscarAeroportosProximos(String codigo){
        return consultaRepository.buscarAeroportosProximos(codigo);
    }

    public AeroportoMaisProximoProjection buscarAeroportoMaisProximo(String codigo){
        return consultaRepository.buscarAeroportoMaisProximo(codigo);
    }

    public AreaMunicipioProjection calcularAreaMunicipio(String codigo){
        return consultaRepository.calcularAreaMunicipio(codigo);
    }

    public PoligonoRodoviasProjection buscarPoligonoRodovias (String codigo){
        return consultaRepository.buscarPoligonoRodovias(codigo);
    }

    public List<RodoviaFerroviaProjection> buscarRodoviasProximasFerrovias (String codigo){
        return consultaRepository.buscarRodoviasProximasFerrovias(codigo);
    }

}
