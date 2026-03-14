#pragma once

#include <string>
#include <vector>
#include <memory>

struct player
{
    int H, A, D, s, S;
};

struct item
{
    int dH, dA, dD, dS;
};

struct point
{
    int row;
    int col;
};

struct Move
{
    char type;   // 'm', 'a', 'p'
    char torow;  // row letter: 'A'..'Z' or 'a'..'z' (or '.' for pass)
    short tocol; // 1-based column (or 0 for pass)
};

struct EngineResult {
    Move move;
    int score;
    double winChance;
};

struct game_state
{
    int H, W, currIdx, oppIdx;
    player players[2];
    std::shared_ptr<std::vector<item>> items;
    std::string s; // encoded map string
};


/* bestMove
 - Core API.
 - Calls sets the stream to the sent file if it exists
 - Computes the best move by calling minimax on all found states*/
EngineResult bestMove(std::istream& fin);



