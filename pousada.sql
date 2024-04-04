-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3307
-- Tempo de geração: 04-Abr-2024 às 14:08
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
  `dataEntrada` date NOT NULL,
  `dataSaida` date NOT NULL,
  `quartos` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `tbl_login`
--

INSERT INTO `tbl_login` (`id`, `nome`, `senha`, `email`, `cpf`, `ddd`, `celular`, `endereco`, `estado`, `cidade`, `dataEntrada`, `dataSaida`, `quartos`) VALUES
(1, 'abc', 'abc', 'abc@gmail.com', '11111111111', '11', '111111111', 'abc district', 'abc land', 'abc city', '2024-03-25', '2024-04-01', 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbl_quartos`
--

CREATE TABLE `tbl_quartos` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `descricao` varchar(600) NOT NULL,
  `preco` decimal(6,2) NOT NULL,
  `diaSemana` enum('Segunda','Terça','Quarta','Quinta','Sexta','Sábado','Domingo') NOT NULL,
  `periodo` enum('Manhã','Tarde','Noite','Madrugada') NOT NULL,
  `disponibilidade` tinyint(1) NOT NULL,
  `pago` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `tbl_quartos`
--
ALTER TABLE `tbl_quartos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
