package br.com.example.controller;

import br.com.example.projection.*;
import br.com.example.service.ConsultaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping("/rodovias/{codigo}")
    public List<RodoviaProjection> buscarRodoviasPorMunicipio(
            @PathVariable String codigo) {

        return consultaService.buscarRodoviasPorMunicipio(codigo);
    }

    @GetMapping("/rodovias/extensao/{codigo}")
    public ExtensaoRodoviaProjection calcularExtensaoRodoviaria(
            @PathVariable String codigo) {
        return consultaService.calcularExtensaoRodoviaria(codigo);
    }

    @GetMapping("/rodovias/jurisdicao/{codigo}/{jurisdicao}")
    public List<RodoviaJuriscricaoProjection> buscarRodoviasPorJuriscricao(
            @PathVariable String codigo,
            @RequestParam String jurisdicao) {
        return consultaService.buscarRodoviasPorJuriscricao(codigo, jurisdicao);
    }

    @GetMapping("/ferrovias/{codigo}")
    public List<FerroviaProjection> buscarFerroviasPorMunicipio(
            @PathVariable String codigo) {
        return consultaService.buscarFerroviasPorMunicipio(codigo);
    }

    @GetMapping("/ferrovias/extensao/{codigo}")
    public ExtensaoFerroviariaProjection calcularExtensaoFerroviaria(
            @PathVariable String codigo) {
        return consultaService.calcularExtensaoFerroviaria(codigo);
    }

    @GetMapping("/aeroportos/{codigo}")
    public List<AeroportoProximoProjection> buscarAeroportosProximos(
            @PathVariable String codigo) {
        return consultaService.buscarAeroportosProximos(codigo);
    }

    @GetMapping("/aeroportos/maisproximo/{codigo}")
    public AeroportoMaisProximoProjection buscarAeroportoMaisProximos(
            @PathVariable String codigo) {
        return consultaService.buscarAeroportoMaisProximo(codigo);
    }

    @GetMapping("/area/municipio/{codigo}")
    public AreaMunicipioProjection calcularAreaMunicipio(
            @PathVariable String codigo) {
        return consultaService.calcularAreaMunicipio(codigo);
    }

    @GetMapping("/rodovias/poligonos/{codigo}")
    public PoligonoRodoviasProjection buscarPoligonoRodovias(
            @PathVariable String codigo){
        return consultaService.buscarPoligonoRodovias(codigo);
    }

    @GetMapping("/rodovias/ferrovias/{codigo}")
    public List<RodoviaFerroviaProjection> buscarRodoviasProximasFerrovias(
            @PathVariable String codigo){
        return consultaService.buscarRodoviasProximasFerrovias(codigo);
    }

}
