#include "gcae.hpp"
#include <iostream>
#include <sstream>
#include <string>

int main()
{
    std::ios::sync_with_stdio(false);
    std::cin.tie(nullptr);

    while (true)
    {
        std::string line;
        std::string fullInput;

        while (std::getline(std::cin, line))
        {
            if (line == "END")
                break;

            fullInput += line + "\n";
        }

        if (fullInput.empty())
            break;

        std::istringstream iss(fullInput);

        Move mv = best_move_from_stream(iss);

        std::cout << mv.type << ' '
                  << mv.torow << ' '
                  << mv.tocol << '\n';

        std::cout.flush();
    }

    return 0;
}
