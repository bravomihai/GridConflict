#include "gcae2.hpp"

#include <algorithm>
#include <cmath>
#include <cctype>
#include <fstream>
#include <iostream>
#include <limits>
#include <string>
#include <vector>

const int INF = std::numeric_limits<int>::max() / 4;

void show_state(const game_state& gs)
{
    std::cout << gs.H << ' '
              << gs.W << ' '
              << gs.currIdx << ' '
              << gs.oppIdx << '\n';

    for (int i = 0; i < 2; ++i)
    {
        const player& p = gs.players[i];
        std::cout << p.H << ' '
                  << p.A << ' '
                  << p.D << ' '
                  << p.s << ' '
                  << p.S << '\n';
    }

    int n = gs.items ? gs.items->size() : 0;
    std::cout << n << '\n';

    if (gs.items)
    {
        for (const item& it : *gs.items)
        {
            std::cout << it.dH << ' '
                      << it.dA << ' '
                      << it.dD << ' '
                      << it.dS << '\n';
        }
    }

    std::cout << gs.s << '\n';
}

EngineResult bestMove(std::istream& fin) {

    EngineResult nullRes = {{'p', '.', 0}, -INF, 0};

    int H, W, depth;
    char current_player;
    fin >> H >> W >> current_player >> depth;
    if (!fin)
        return nullRes;

    game_state gs;
    fin >> gs.players[0].H >> gs.players[0].A >> gs.players[0].D >> gs.players[0].s >> gs.players[0].S;
    fin >> gs.players[1].H >> gs.players[1].A >> gs.players[1].D >> gs.players[1].s >> gs.players[1].S;

    int n;
    fin >> n;
    std::vector<item> items(n);
    for (int i = 0; i < n; ++i)
    {
        fin >> items[i].dH >> items[i].dA >> items[i].dD >> items[i].dS;
    }

    std::string rest;
    std::getline(fin, rest); // eat endline
    std::getline(fin, gs.s);

    if (!gs.s.empty() && gs.s.back() == '\r')
        gs.s.pop_back();

    gs.items = std::make_shared<std::vector<item>>(items);

    show_state(gs);
    
    return nullRes;
}