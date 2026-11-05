package br.com.example.repository;

import br.com.example.model.Municipio;
import br.com.example.projection.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsultaRepository extends JpaRepository<Municipio, Integer> {


    @Query(value = """
    SELECT
        r.gid AS gid,
        r.sigla AS sigla,
        r.tipovia AS tipovia,
        r.jurisdicao AS jurisdicao,
        r.revestimen AS revestimen,
        r.operaciona AS operaciona,
        r.nrpistas AS nrpistas
    FROM rod_trecho_rodoviario_l r
    INNER JOIN br_municipios_2025 m
        ON ST_Intersects(r.geom, m.geom)
    WHERE m.cd_mun = :codigo
    """, nativeQuery = true)
    List<RodoviaProjection> buscarRodoviasPorMunicipio(
            @Param("codigo") String codigo
    );


    @Query(value = """
    SELECT
        m.nm_mun AS municipio,
        SUM(
            ST_Length(
                CAST(ST_Intersection(r.geom, m.geom) AS geography)
            )
        ) / 1000.0 AS "extensaoKm"
    FROM rod_trecho_rodoviario_l r
    INNER JOIN br_municipios_2025 m
        ON ST_Intersects(r.geom, m.geom)
    WHERE m.cd_mun = :codigo
    GROUP BY m.nm_mun
    """, nativeQuery = true)
    ExtensaoRodoviaProjection calcularExtensaoRodoviaria(
            @Param("codigo") String codigo
    );

    @Query(value = """
    SELECT
        r.gid AS gid,
        r.sigla AS sigla,
        r.tipovia AS tipovia,
        r.jurisdicao AS jurisdicao,
        r.revestimen AS revestimen,
        r.operaciona AS operaciona,
        r.nrpistas AS nrpistas
    FROM rod_trecho_rodoviario_l r
    INNER JOIN br_municipios_2025 m
        ON ST_Intersects(r.geom, m.geom)
    WHERE m.cd_mun = :codigo
      AND r.jurisdicao = :jurisdicao
    """, nativeQuery = true)
    List<RodoviaJuriscricaoProjection> buscarRodoviasPorJuriscricao(
            @Param("codigo") String codigo,
            @Param("jurisdicao") String jurisdicao
    );

    @Query(value = """
    SELECT
        f.gid AS gid,
        f.nome AS nome,
        f.bitola AS bitola,
        f.eletrifica AS eletrifica,
        f.jurisdicao AS jurisdicao,
        f.operaciona AS operaciona,
        f.situacaofi AS situacaofi
    FROM fer_trecho_ferroviario_l f
    INNER JOIN br_municipios_2025 m
        ON ST_Intersects(f.geom, m.geom)
    WHERE m.cd_mun = :codigo
    """, nativeQuery = true)
    List<FerroviaProjection> buscarFerroviasPorMunicipio(
            @Param("codigo") String codigo
    );

    @Query(value = """
    SELECT
        m.nm_mun AS municipio,
        SUM(
            ST_Length(
                CAST(ST_Intersection(f.geom, m.geom) AS geography)
            )
        ) / 1000.0 AS "extensaoKm"
    FROM fer_trecho_ferroviario_l f
    INNER JOIN br_municipios_2025 m
        ON ST_Intersects(f.geom, m.geom)
    WHERE m.cd_mun = :codigo
    GROUP BY m.nm_mun
    """, nativeQuery = true)
    ExtensaoFerroviariaProjection calcularExtensaoFerroviaria(
            @Param("codigo") String codigo
    );

    @Query(value = """
    SELECT
        a.gid AS gid,
        a.nome AS nome,
        a.modaluso AS modaluso,
        a.administra AS administra,
        a.situacaofi AS situacaofi,
        ST_Distance(
            CAST(a.geom AS geography),
            CAST(m.geom AS geography)
        ) / 1000.0 AS "distanciaKm"
    FROM aer_complexo_aeroportuario_p a
    INNER JOIN br_municipios_2025 m
        ON ST_DWithin(
            CAST(a.geom AS geography),
            CAST(m.geom AS geography),
            10000
        )
    WHERE m.cd_mun = :codigo
    ORDER BY "distanciaKm"
    """, nativeQuery = true)
    List<AeroportoProximoProjection> buscarAeroportosProximos(
            @Param("codigo") String codigo
    );

    @Query(value = """
    SELECT
        a.gid AS gid,
        a.nome AS nome,
        a.modaluso AS modaluso,
        a.administra AS administra,
        a.situacaofi AS situacaofi,
        ST_Distance(
            CAST(a.geom AS geography),
            CAST(m.geom AS geography)
        ) / 1000.0 AS "distanciaKm"
    FROM aer_complexo_aeroportuario_p a
    CROSS JOIN br_municipios_2025 m
    WHERE m.cd_mun = :codigo
    ORDER BY "distanciaKm"
    LIMIT 1
    """, nativeQuery = true)
    AeroportoMaisProximoProjection buscarAeroportoMaisProximo(
            @Param("codigo") String codigo
    );

    @Query(value = """
    SELECT
        m.cd_mun AS codigo,
        m.nm_mun AS municipio,
        ST_Area(
            CAST(m.geom AS geography)
        ) / 1000000.0 AS "areaKm2"
    FROM br_municipios_2025 m
    WHERE m.cd_mun = :codigo
    """, nativeQuery = true)
    AreaMunicipioProjection calcularAreaMunicipio(
            @Param("codigo") String codigo
    );

    @Query(value = """
    SELECT
        m.nm_mun AS municipio,
        ST_AsText(
            ST_ConvexHull(
                ST_Collect(r.geom)
            )
        ) AS "poligonoEnvolvente"
    FROM rod_trecho_rodoviario_l r
    INNER JOIN br_municipios_2025 m
        ON ST_Intersects(r.geom, m.geom)
    WHERE m.cd_mun = :codigo
    GROUP BY m.nm_mun
    """, nativeQuery = true)
    PoligonoRodoviasProjection buscarPoligonoRodovias(
            @Param("codigo") String codigo
    );

    @Query(value = """
    SELECT DISTINCT
        r.gid AS "rodoviaId",
        r.sigla AS rodovia,
        r.tipovia AS tipovia,
        f.gid AS "ferroviaId",
        f.nome AS ferrovia
    FROM rod_trecho_rodoviario_l r
    INNER JOIN fer_trecho_ferroviario_l f
        ON ST_Intersects(
            CAST(r.geom AS geography),
            ST_Buffer(
                CAST(f.geom AS geography),
                500
            )
        )
    INNER JOIN br_municipios_2025 m
        ON ST_Intersects(r.geom, m.geom)
        AND ST_Intersects(f.geom, m.geom)
    WHERE m.cd_mun = :codigo
    ORDER BY r.sigla
    """, nativeQuery = true)
    List<RodoviaFerroviaProjection> buscarRodoviasProximasFerrovias(
            @Param("codigo") String codigo
    );
}
