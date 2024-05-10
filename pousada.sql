-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3307
-- Tempo de geração: 10-Maio-2024 às 19:35
-- Versão do servidor: 10.4.32-MariaDB
-- versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `pousada`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbl_login`
--

CREATE TABLE `tbl_login` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `senha` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `cpf` char(11) NOT NULL,
  `ddd` char(2) NOT NULL,
  `celular` char(9) NOT NULL,
  `endereco` varchar(200) NOT NULL,
  `estado` varchar(100) NOT NULL,
  `cidade` varchar(150) NOT NULL,
  `admin` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `tbl_login`
--

INSERT INTO `tbl_login` (`id`, `nome`, `senha`, `email`, `cpf`, `ddd`, `celular`, `endereco`, `estado`, `cidade`, `admin`) VALUES
(1, 'abc', 'abc', 'abc@gmail.com', '11111111111', '11', '111111111', 'abc district', 'abc land', 'abc city', 1),
(8, '123', '123', '123@123.com', '12312312312', '12', '123123123', '123 china', '123 state', '123 city', 0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbl_quartos`
--

CREATE TABLE `tbl_quartos` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `descricao` varchar(600) NOT NULL,
  `preco` decimal(6,2) NOT NULL,
  `dataEntrada` date NOT NULL,
  `dataSaida` date NOT NULL,
  `idUsuario` int(11) DEFAULT NULL,
  `disponibilidade` tinyint(1) NOT NULL,
  `pago` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `tbl_quartos`
--

INSERT INTO `tbl_quartos` (`id`, `nome`, `descricao`, `preco`, `dataEntrada`, `dataSaida`, `idUsuario`, `disponibilidade`, `pago`) VALUES
(1, '101 Quarto Deluxe', 'Quarto espaçoso com vista para o mar', 150.00, '2024-10-25', '2024-10-27', NULL, 1, 0),
(2, '102 Suíte Executiva', 'Suíte luxuosa com jacuzzi privativa', 250.00, '2020-10-25', '2024-05-05', NULL, 1, 0),
(3, '103 Quarto Standard', 'Quarto confortável com todas as comodidades necessárias', 100.00, '2024-05-20', '2024-05-23', NULL, 1, 0),
(4, '104 Quarto Familiar', 'Espaçoso quarto com capacidade para toda a família', 200.00, '2024-05-20', '2024-05-28', NULL, 1, 0),
(5, '105 Suíte Presidencial', 'A suíte mais luxuosa do hotel, com serviço de mordomo 24 horas', 1500.00, '2024-10-25', '2024-10-25', NULL, 1, 0),
(6, '106 Quarto Standard Duplo', 'Quarto confortável com duas camas de solteiro', 120.00, '2024-05-08', '2024-05-09', NULL, 1, 0),
(7, '107 Quarto Econômico', 'Opção econômica para estadias curtas', 80.00, '2024-10-05', '2024-10-07', NULL, 1, 0),
(8, '108 Suíte Júnior', 'Suíte elegante com área de estar separada', 180.00, '2024-11-01', '2024-11-05', NULL, 1, 0),
(9, '109 Quarto com Vista para o Jardim', 'Quarto tranquilo com vista para os belos jardins do hotel', 140.00, '2024-05-09', '2024-05-31', NULL, 1, 0),
(10, '110 Suíte Familiar', 'Espaçosa suíte com dois quartos interligados', 300.00, '2025-01-10', '2025-01-15', NULL, 1, 0),
(11, '201 Quarto Superior', 'Quarto elegante com comodidades de alta qualidade', 180.00, '2025-02-05', '2025-02-10', NULL, 1, 0),
(12, '202 Suíte de Luxo', 'Suíte sofisticada com vista panorâmica da cidade', 400.00, '2025-03-15', '2025-03-20', NULL, 1, 0),
(13, '203 Quarto Adaptado', 'Quarto projetado para atender às necessidades de hóspedes com mobilidade reduzida', 150.00, '2025-04-10', '2025-04-15', NULL, 1, 0),
(14, '204 Quarto com Varanda', 'Quarto com varanda privativa e vista deslumbrante', 160.00, '2025-05-20', '2025-05-25', NULL, 1, 0),
(15, '205 Suíte Spa', 'Suíte com banheira de hidromassagem privativa e serviços de spa', 350.00, '2025-06-10', '2025-06-15', NULL, 1, 0),
(16, '206 Quarto Panorâmico', 'Quarto com janelas panorâmicas oferecendo vista panorâmica da cidade', 200.00, '2025-07-05', '2025-07-10', NULL, 1, 0),
(17, '207 Suíte Romance', 'Suíte decorada romanticamente para casais', 250.00, '2025-08-15', '2025-08-20', NULL, 1, 0),
(18, '208 Quarto Premium', 'Quarto espaçoso com móveis de design e amenidades premium', 220.00, '2025-09-20', '2025-09-25', NULL, 1, 0),
(19, '209 Quarto Temático', 'Quarto temático inspirado em um tema específico', 180.00, '2025-10-10', '2025-10-15', NULL, 1, 0),
(20, '210 Suíte Temática', 'Suíte decorada com um tema único e especial', 200.00, '2025-11-01', '2025-11-05', NULL, 1, 0),
(21, '301 Quarto Deluxe King', 'Quarto luxuoso com uma cama king-size', 180.00, '2025-12-10', '2025-12-15', NULL, 1, 0),
(22, '302 Suíte Oceanfront', 'Suíte com vista deslumbrante para o oceano', 300.00, '2026-01-05', '2026-01-10', NULL, 1, 0),
(23, '303 Quarto Premium Duplo', 'Quarto espaçoso com duas camas queen-size', 240.00, '2026-02-15', '2026-02-20', NULL, 1, 0),
(24, '304 Suíte Master', 'Suíte de luxo com todas as comodidades modernas', 450.00, '2026-03-20', '2026-03-25', NULL, 1, 0),
(25, '305 Quarto com Terraço', 'Quarto com um terraço privativo', 160.00, '2026-04-10', '2026-04-15', NULL, 1, 0),
(26, '306 Suíte Skyline', 'Suíte com vista panorâmica da cidade', 280.00, '2026-05-20', '2026-05-25', NULL, 1, 0),
(27, '307 Quarto de Luxo', 'Quarto elegante com todas as comodidades de luxo', 220.00, '2026-06-10', '2026-06-15', NULL, 1, 0),
(28, '308 Suíte Loft', 'Suíte com design moderno e área de loft', 320.00, '2026-07-05', '2026-07-10', NULL, 1, 0),
(29, '309 Quarto Standard Twin', 'Quarto confortável com duas camas de solteiro', 130.00, '2026-08-15', '2026-08-20', NULL, 1, 0),
(30, '310 Suíte VIP', 'Suíte exclusiva com serviço de mordomo e acesso a áreas VIP', 500.00, '2024-10-10', '2024-10-10', NULL, 1, 0);

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `tbl_login`
--
ALTER TABLE `tbl_login`
  ADD PRIMARY KEY (`id`);

--
-- Índices para tabela `tbl_quartos`
--
ALTER TABLE `tbl_quartos`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `tbl_login`
--
ALTER TABLE `tbl_login`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de tabela `tbl_quartos`
--
ALTER TABLE `tbl_quartos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
